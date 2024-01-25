package com.example.gymbros.viewModels

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gymbros.MainActivity
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel() {
    private val _authStatus = MutableLiveData<Boolean>()
    val authStatus: LiveData<Boolean> get() = _authStatus
    private var auth: FirebaseAuth = Firebase.auth

    fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authStatus.postValue(true)
                } else {
                    _authStatus.postValue(false)
                }
            }
    }
    fun signUpWithEmailAndPassword(email: String, password: String, username: String, navController: NavController) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    _authStatus.postValue(true)
                    throw task.exception!!
                } else {
                    _authStatus.postValue(false)
                }

                val user = auth.currentUser
                val db = Firebase.firestore
                db.collection("users").document(user?.uid ?: "")
                    .set(mapOf("username" to username))
                    .addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error writing document", e)
                    }
                Tasks.forResult(null)
            }
    }

    fun signOut(context: Context) {
        auth.signOut()
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}