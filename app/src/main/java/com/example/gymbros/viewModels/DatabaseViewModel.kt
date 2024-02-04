package com.example.gymbros.viewModels

import android.content.ContentValues.TAG
import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.gymbros.User
import com.example.gymbros.Workout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DatabaseViewModel : ViewModel() {
    private val database = Firebase.firestore
    private var lastVisible: DocumentSnapshot? = null
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val usersRef = db.collection("users")

    private var friendsId = mutableListOf<String>()
    private var friendRequestsId = mutableListOf<String>()
    private var workoutsId = mutableListOf<String>()

    val friendRequests = mutableListOf<User>()
    val listOfFriends = mutableListOf<User>()

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
                    lastVisible = null //to jest żeby w kółko pobierało
                } else {
                    val user = documentSnapshots.documents[0]
                    val username = user.getString("username")
                    val preference = user.getString("preference")

                    if (username != currentUser.value.username && !friendsId.contains(user.id) && preference == currentUser.value.preference) {
                        val bio = user.getString("bio")
                        fetchedUser.value = User(user.id, username, preference, bio)
                    }
                    lastVisible = user
                    fetchNextUser()
                }
            }.addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
        } catch (e: Exception) {
            println("Error executing fetchNextUser: $e")
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
                    currentUser.value = User(uid, username, preference, bio)
                }
            }
        }
        if (fetchedUser.value.id == "") fetchNextUser()
        getWorkouts()
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
                    //val friends = documentSnapshot.getString("friends")

                    if (friendsId.contains(id) && !listOfFriends.contains(
                            User(
                                id,
                                username,
                                preference,
                                bio
                            )
                        )
                    ) {
                        listOfFriends.add(User(id, username, preference, bio))
                    }
                    if (friendRequestsId.contains(id) && !friendRequests.contains(
                            User(
                                id,
                                username,
                                preference,
                                bio
                            )
                        )
                    ) {
                        friendRequests.add(User(id, username, preference, bio))
                    }
                }
            }
        }
    }


    fun sendFriendRequest() {
        val uid = auth.currentUser?.uid
        val fetcheduid = fetchedUser.value.id
        //if (fetcheduid != "null") {
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
        //}

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

    fun updateLocation(location: Location) {
        val userId = auth.currentUser?.uid ?: ""
        val userRef = db.collection("users").document(userId)
        userRef.update("location", GeoPoint(location.latitude, location.longitude))
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

                    //change id of participants to usernames
                    val participantsUsername = changeFromIdToUsername(participants)

                    if(workoutsId.contains(id)) {
                        if(!listOfWorkouts.contains(Workout(id, type, description, date, duration, participantsUsername))) {
                            listOfWorkouts.add(Workout(id, type, description, date, duration, participantsUsername))
                        }
                    }
                }
            }
        }


        val formatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        listOfWorkouts.sortWith { o1, o2 ->
            val date1 = o1.date?.takeIf { it.isNotEmpty() }?.let { formatter.parse(it) } ?: Date(Long.MIN_VALUE)
            val date2 = o2.date?.takeIf { it.isNotEmpty() }?.let { formatter.parse(it) } ?: Date(Long.MIN_VALUE)
            date2.compareTo(date1)
        }
    }

    fun registerWorkout(workout: Workout) {
        workout.participants?.add(currentUser.value.username!!)
        val participantsid = changeFromUsernametoId(workout.participants!!)

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
                db.collection("workouts").document(documentReference.id).update("id", documentReference.id)
                Log.d(TAG, "Workout added with ID: ${documentReference.id}")

                db.collection("users").get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documents = task.result?.documents
                        documents?.forEach { documentSnapshot ->
                            val id = documentSnapshot.id
                            //val username = documentSnapshot.getString("username")
                            if (participantsid.contains(id)) {
                                val userWorkouts = documentSnapshot.get("workouts") as MutableList<String>
                                userWorkouts.add(documentReference.id)
                                usersRef.document(id).update("workouts", userWorkouts)
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

    private fun changeFromIdToUsername(list: MutableList<String>): MutableList<String> {
        val namesList = mutableListOf<String>()
        db.collection("users").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                documents?.forEach { documentSnapshot ->
                    val id = documentSnapshot.id
                    if(list.contains(id)) {
                        val username = documentSnapshot.getString("username")
                        namesList.add(username!!)
                    }
                }
            }
        }
        return namesList
    }
    private fun changeFromUsernametoId(list: MutableList<String>): MutableList<String> {
        val idList = mutableListOf<String>()
        db.collection("users").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                documents?.forEach { documentSnapshot ->
                    val username = documentSnapshot.getString("username")
                    if (list.contains(username)) {
                        val id = documentSnapshot.id
                        idList.add(id)
                    }
                }
            }
        }
        return idList
    }
}


