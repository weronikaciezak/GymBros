package com.example.gymbros.viewModels

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.gymbros.User
import com.example.gymbros.Workout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class DatabaseViewModel : ViewModel() {
    private val database = Firebase.firestore
    private var lastVisible: DocumentSnapshot? = null
    private var lastVisibleChallenge: DocumentSnapshot? = null

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val usersRef = db.collection("users")

    private var friendsId = mutableListOf<String>()
    private var friendRequestsId = mutableListOf<String>()
    private var workoutsId = mutableListOf<String>()

    var challenges = mutableListOf<String>()
    val fetchedChallenge = mutableStateOf("You have no challenges.")

    val friendRequests = mutableListOf<User>()
    val listOfFriends = mutableListOf<User>()
    private val allUsers = mutableListOf<User>()

    val listOfWorkouts = mutableListOf<Workout>()

    val fetchedUser = mutableStateOf(User("", "username", "null", "null"))
    val currentUser = mutableStateOf(User("", "username", "null", "null"))

    fun fetchNextUser() {
        try {
            var query = database.collection("users").limit(1)

            if (lastVisible != null) {
                query = query.startAfter(lastVisible!!)
            }

            query.get().addOnSuccessListener { documentSnapshots ->
                if (documentSnapshots.isEmpty) {
                    println("No more users.")
                    fetchedUser.value = User(
                        "null",
                        "username",
                        "null",
                        "null"
                    ) //TODO change the id to "" if problems occur
                    lastVisible = null //to jest żeby w kółko pobierało
                } else {
                    val user = documentSnapshots.documents[0]
                    val username = user.getString("username")
                    val preference = user.getString("preference")

                    if (username != currentUser.value.username && !friendsId.contains(user.id) && preference == currentUser.value.preference) {
                        val bio = user.getString("bio")
                        fetchedUser.value = User(user.id, username, preference, bio)
                    } else {
                        fetchNextUser()
                    }
                    lastVisible = user
                }
            }.addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
        } catch (e: Exception) {
            println("Error executing fetchNextUser: $e")
        }
    }

    fun fetchDataFromFirebase() {
        usersRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                documents?.forEach { documentSnapshot ->
                    val id = documentSnapshot.id
                    val username = documentSnapshot.getString("username")
                    val preference = documentSnapshot.getString("preference")
                    val bio = documentSnapshot.getString("bio")

                    val user = User(id, username, preference, bio)
                    if (!allUsers.contains(user)) {
                        allUsers.add(user)
                    }
                    if (friendsId.contains(id) && !listOfFriends.contains(user)) {
                        listOfFriends.add(user)
                    }
                    if (friendRequestsId.contains(id) && !friendRequests.contains(user)) {
                        friendRequests.add(user)
                    }
                }
            }
        }
    }


    fun getUserData() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            usersRef.document(uid).get().addOnSuccessListener { document ->
                if (document != null) {
                    val username = document.getString("username")
                    val preference = document.getString("preference")
                    val bio = document.getString("bio")
                    friendsId = document.get("friends") as MutableList<String>
                    friendRequestsId = document.get("friend-requests") as MutableList<String>
                    workoutsId = document.get("workouts") as MutableList<String>
                    challenges = document.get("challenges") as MutableList<String>
                    currentUser.value = User(uid, username, preference, bio)
                }
            }
        }
        if (fetchedUser.value.id == "") fetchNextUser()
    }


    fun sendFriendRequest() {
        val uid = auth.currentUser?.uid
        val fetcheduid = fetchedUser.value.id
        if (fetcheduid != null) {
            usersRef.document(fetcheduid).get().addOnSuccessListener { document ->
                if (document != null) {
                    val friendRequests = document.get("friend-requests") as MutableList<String>
                    if (!friendRequests.contains(uid) || friendRequests.isEmpty()) {
                        if (uid != null) {
                            friendRequests.add(uid)
                        }
                        usersRef.document(fetcheduid).update("friend-requests", friendRequests)
                    } else {
                        Log.d(TAG, "friend request already sent")
                    }
                }
            }
        }
    }

    fun acceptFriendRequest(user: User) {
        val id = user.id!!
        val uid = auth.currentUser?.uid
        if (uid != null) {
            //current user
            usersRef.document(uid).get().addOnSuccessListener { document ->
                if (document != null) {
                    val friendRequests = document.get("friend-requests") as MutableList<String>
                    if (friendRequests.contains(id)) {
                        friendRequests.remove(id)

                        usersRef.document(uid).update("friend-requests", friendRequests)
                        val friends = document.get("friends") as MutableList<String>
                        if (!friends.contains(id)) {
                            friends.add(id)
                            usersRef.document(uid).update("friends", friends)
                        } else {
                            Log.d(TAG, "friend already added")
                        }
                    }
                }
            }
        }
        //sender of the request
        usersRef.document(id).get().addOnSuccessListener { document ->
            if (document != null) {
                val friends = document.get("friends") as MutableList<String>
                if (!friends.contains(uid) && uid != null) {
                    friends.add(uid)
                    usersRef.document(id).update("friends", friends)
                } else {
                    Log.d(TAG, "friend already added")
                }
            }
        }
        friendRequests.remove(user)
    }

    fun getWorkouts() {
        db.collection("workouts").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                documents?.forEach { documentSnapshot ->
                    val id = documentSnapshot.id
                    val type = documentSnapshot.getString("type")
                    val description = documentSnapshot.getString("description")
                    val date = documentSnapshot.getString("date")
                    val duration = documentSnapshot.getString("duration")
                    val participants = documentSnapshot.get("participants") as MutableList<String>
                    val tmp = mutableListOf<String>()

                    db.collection("users").get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val document = task.result?.documents
                            document?.forEach { documentSnap ->
                                val username = documentSnap.getString("username")
                                if (participants.contains(documentSnap.id)) {
                                    if (username != null) {
                                        tmp.add(username)
                                    }
                                }
                            }
                        }
                    }
                    val workout = Workout(id, type, description, date, duration, tmp)
                    if (workoutsId.contains(workout.id)) {
                        if (!listOfWorkouts.contains(workout)) {
                            listOfWorkouts.add(workout)
                        }
                    }
                }
            }
        }

//        val formatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
//        listOfWorkouts.sortWith { o1, o2 ->
//            val date1 = o1.date?.takeIf { it.isNotEmpty() }?.let { formatter.parse(it) } ?: Date(Long.MIN_VALUE)
//            val date2 = o2.date?.takeIf { it.isNotEmpty() }?.let { formatter.parse(it) } ?: Date(Long.MIN_VALUE)
//            date2.compareTo(date1)
//        }
    }

    fun registerWorkout(workout: Workout, participantsid: MutableList<String>) {
        if (workout.description != "You have no challenges.") {

            val workoutData = hashMapOf(
                "id" to workout.id,
                "type" to workout.type,
                "duration" to workout.duration,
                "date" to workout.date,
                "description" to workout.description,
                "participants" to participantsid
            )
            db.collection("workouts").add(workoutData)
                .addOnSuccessListener { documentReference ->
                    db.collection("workouts").document(documentReference.id)
                        .update("id", documentReference.id)
                    db.collection("workouts").document(documentReference.id)
                        .update("participants", participantsid)


                    db.collection("users").get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val documents = task.result?.documents
                            documents?.forEach { documentSnapshot ->
                                val id = documentSnapshot.id

                                if (participantsid.contains(id)) {
                                    val userWorkouts =
                                        documentSnapshot.get("workouts") as MutableList<String>
                                    userWorkouts.add(documentReference.id)
                                    usersRef.document(id).update("workouts", userWorkouts)
                                }
                            }
                        }
                    }
                }
        }
    }

    fun editProfile(username: String, preference: String, bio: String) {
        val userId = auth.currentUser?.uid ?: ""
        val userRef = db.collection("users").document(userId)
        userRef.update("username", username)
        userRef.update("preference", preference)
        userRef.update("bio", bio)
    }

    fun changeFromUsernametoId(list: MutableList<String>): MutableList<String> {
        val idList = mutableListOf<String>()
        db.collection("users").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                documents?.forEach { documentSnapshot ->
                    val username = documentSnapshot.getString("username")
                    if (list.contains(username)) {
                        val id = documentSnapshot.getString("id")
                        if (id != null) {
                            idList.add(id)
                        }
                    }
                }
            }
        }
        return idList
    }

    fun sendChallenge(selectedFriends: MutableList<String>, text: String, context: Context) {
        val challengeData = hashMapOf(
            "id" to "null",
            "text" to text
        )
        db.collection("challenges").add(challengeData)
            .addOnSuccessListener { documentReference ->
                db.collection("challenges").document(documentReference.id)
                    .update("id", documentReference.id)

                usersRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documents = task.result?.documents
                        documents?.forEach { documentSnapshot ->
                            val username = documentSnapshot.getString("username")
                            if (selectedFriends.contains(username)) {
                                val challenges =
                                    documentSnapshot.get("challenges") as MutableList<String>
                                challenges.add(documentReference.id)
                                usersRef.document(documentSnapshot.id)
                                    .update("challenges", challenges)
                                Toast.makeText(context, "Challenge sent!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
    }


    fun deleteChallenge(challenge: String) {
        val userId = auth.currentUser?.uid ?: ""
        val userRef = db.collection("users").document(userId)
        userRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val challenges = document.get("challenges") as MutableList<String>
                challenges.remove(challenge)
                userRef.update("challenges", challenges)
            }
        }
        challenges.remove(challenge)
    }

    fun fetchNextChallenge() {
        try {
            var query = database.collection("challenges").limit(1)

            if (lastVisibleChallenge != null) {
                query = query.startAfter(lastVisibleChallenge!!)
            }

            query.get().addOnSuccessListener { documentSnapshots ->
                if (documentSnapshots.isEmpty) {
                    println("You have no challenges.")
                    fetchedChallenge.value = "You have no challenges."
                } else {
                    val challenge = documentSnapshots.documents[0]
                    val text = challenge.getString("text")
                    val id = challenge.getString("id")
                    if (challenges.contains(id)) {
                        if (text != null) {
                            fetchedChallenge.value = text
                        }
                    } else {
                        fetchNextUser()
                    }
                    lastVisibleChallenge = challenge
                }
            }.addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
        } catch (e: Exception) {
            println("Error executing fetchNextChallenge: $e")
        }
    }

}



