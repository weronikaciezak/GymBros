package com.example.gymbros.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.gymbros.shit.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class DatabaseViewModel : ViewModel() {
    private val database = Firebase.firestore
    private var lastVisible: DocumentSnapshot? = null
    private val auth = FirebaseAuth.getInstance()
    private val uid = auth.currentUser?.uid
    private val db = FirebaseFirestore.getInstance()
    private val usersRef = db.collection("users")

    val userData = mutableStateOf("did not fetch yet")
    var currentUsername = "username"
    var friendslist = mutableListOf<User>()

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
                val username = user.getString("username")
                userData.value = username ?: "null"
                lastVisible = user
            } else {
                println("No more users.")
                //lastVisible = null //to jest żeby w kółko pobierało
            }
        }
    }

    fun getUsername() {

        if (uid != null) {
            usersRef.document(uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        currentUsername = document.getString("username").toString()
                        Log.d(TAG, "user: $currentUsername")
                    } else {
                        Log.d(TAG, "The document doesn't exist.")
                    }
                } else {
                    task.exception?.message?.let {
                        Log.d(TAG, it)
                    }
                }
            }
        }
    }

    fun addFriend(userId: String) {
        if (uid != null) {
            usersRef.document(uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        val friends = document.get("friends") as MutableList<String>
                        friends.add(userData.value)
                        usersRef.document(uid).update("friends", friends)
                        Log.d(TAG, "user: $currentUsername")
                    } else {
                        Log.d(TAG, "The document doesn't exist.")
                    }
                } else {
                    task.exception?.message?.let {
                        Log.d(TAG, it)
                    }
                }
            }
        }
    }

}
