package com.example.gymbros.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.gymbros.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class DatabaseViewModel : ViewModel() {
    private val database = Firebase.firestore
    private var lastVisible: DocumentSnapshot? = null
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val usersRef = db.collection("users")


    var friendsNames = mutableListOf<String>()
    private var friendsId = mutableListOf<String>()
    private var friendRequests = mutableListOf<String>()
    val allUsernames = mutableListOf<String>()


    val list = mutableListOf<User>()


    var userId = mutableStateOf("")
    val userData = mutableStateOf("did not fetch id yet")
    val currentUsername = mutableStateOf("did not fetch yet")

    fun fetchNextUser() {
        var query = database.collection("users")
            .orderBy("username")
            .limit(1)

        if (lastVisible != null) {
            query = query.startAfter(lastVisible!!)
        }

        query.get().addOnSuccessListener { documentSnapshots ->
            if (!documentSnapshots.isEmpty) {
                val user = documentSnapshots.documents[0]
                println(user.id + " => " + user.data)
                userId.value = user.id
                val username = user.getString("username")
                if (username == currentUsername.value || list.contains(
                        User(
                            userId.value,
                            null,
                            null
                        )
                    )
                ) {//friendsId.contains(userId.value)) {
                    fetchNextUser()
                } else {
                    userData.value = username ?: "null"
                }
                lastVisible = user
            } else {
                println("No more users.")
                lastVisible = null //to jest żeby w kółko pobierało
            }
        }

    }

    fun getUsername() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            usersRef.document(uid).get().addOnSuccessListener { document ->
                if (document != null) {
                    val username = document.getString("username")
                    friendsId = document.get("friends") as MutableList<String>
                    //friendRequests = document.get("friend-requests") as MutableList<String>
                    currentUsername.value = username ?: "null"
                }
            }
        }
    }

    fun fetchDataFromFirebase() {
        usersRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                documents?.forEach { documentSnapshot ->
                    val id = documentSnapshot.id
                    val username = documentSnapshot.getString("username")
                    val style = documentSnapshot.getString("style") ?: "null"
                    //val friends = documentSnapshot.getString("friends")
                    if (friendsId.contains(id) && !list.contains(User(id, username, style))) {
                        list.add(User(id, username, style))
                    }
                }
            }
        }
    }


    fun sendFriendRequest() {
        val uid = auth.currentUser?.uid
        usersRef.document(userId.value).get().addOnSuccessListener { document ->
            if (document != null) {
                val friendRequests = document.get("friend-requests") as MutableList<String>
                if (!friendRequests.contains(uid) || friendRequests.isEmpty()) {
                    if (uid != null) {
                        friendRequests.add(uid)
                    }
                    usersRef.document(userId.value).update("friend-requests", friendRequests)
                } else {
                    Log.d(TAG, "friend request already sent")
                }
            }
        }
    }

    fun acceptFriendRequest(id: String) {
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
    }


//    fun getFriends() {
//        val uid = auth.currentUser?.uid
//        if (uid != null) {
//            usersRef.document(uid).get().addOnSuccessListener { document ->
//                if (document != null) {
//                    friendsId = document.get("friends") as MutableList<String>
//                    //friendRequests = document.get("friend-requests") as MutableList<String>
//                }
//            }
//        }
//    }


//    fun getFriendsNames() {
//        for(name in friendsId) {
//            usersRef.document(name).get().addOnSuccessListener { document ->
//                if (document != null) {
//                    friendsNames.add(document.getString("username") ?: "null")
//                    val id = document.id
//                    val username = document.getString("username")
//                    val style = document.getString("style") ?: "null"
//                    //val friends = documentSnapshot.getString("friends")
//
//                    if (username != null) {
//                        list.add(User(id, username, style))
//                        Log.d(ContentValues.TAG, "Username: $username")
//                    }
//                }
//            }
//        }
//    }
}

