package com.example.gymbros.viewModels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gymbros.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel() {
    private val _authStatus = MutableLiveData<Boolean>()
    val authStatus: LiveData<Boolean> get() = _authStatus
    private val _authRegStatus = MutableLiveData<Boolean>()
    val authRegStatus: LiveData<Boolean> get() = _authRegStatus
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

    fun signUpWithEmailAndPassword(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    _authRegStatus.postValue(false)
                    throw task.exception!!
                } else {
                    _authRegStatus.postValue(true)

                    val user = auth.currentUser
                    val userId = user?.uid ?: ""
                    val db = Firebase.firestore
                    val emptylist = arrayListOf("")
                    val userData = hashMapOf(
                        "id" to userId,
                        "username" to username,
                        "preference" to "null",
                        "friends" to emptylist,
                        "friend-requests" to emptylist,
                        "location" to "null",

                        "bio" to "no bio yet",
                    )
                    db.collection("users").document(userId).set(userData)
                }

                //Tasks.forResult(null)
            }
    }

    fun signOut(context: Context) {
        auth.signOut()
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
    fun setPreference(preference: String) {
        val user = auth.currentUser
        val userId = user?.uid ?: ""
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userId).update("preference", preference)
    }
}