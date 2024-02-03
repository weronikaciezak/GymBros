package com.example.gymbros.viewModels

import android.content.ContentValues.TAG
import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.gymbros.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.firestore

class DatabaseViewModel : ViewModel() {
    private val database = Firebase.firestore
    private var lastVisible: DocumentSnapshot? = null
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val usersRef = db.collection("users")

    private var friendsId = mutableListOf<String>()
    private var friendRequestsId = mutableListOf<String>()

    val friendRequests = mutableListOf<User>()
    val listOfFriends = mutableListOf<User>()

    val fetchedUser = mutableStateOf(User("", "null", "null", "null"))
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

                    if (username != currentUser.value.username && !friendsId.contains(user.id)) {
                        val preference = user.getString("preference")
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

    fun getUsername() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            usersRef.document(uid).get().addOnSuccessListener { document ->
                if (document != null) {
                    val username = document.getString("username")
                    val preference = document.getString("preference")
                    val bio = document.getString("bio")
                    friendsId = document.get("friends") as MutableList<String>
                    friendRequestsId = document.get("friend-requests") as MutableList<String>
                    currentUser.value = User(uid, username, preference, bio)
                }
            }
        }
        if (fetchedUser.value.id == "") fetchNextUser()
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

    fun updateLocation(location: Location) {
        val userId = auth.currentUser?.uid ?: ""
        val userRef = db.collection("users").document(userId)
        userRef.update("location", GeoPoint(location.latitude, location.longitude))
    }
}


