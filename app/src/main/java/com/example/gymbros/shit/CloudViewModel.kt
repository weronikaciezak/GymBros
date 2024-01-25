package com.example.gymbros.shit

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CloudViewModel : ViewModel() {

    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)
    init {
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val list = mutableListOf<User>()
        val db = Firebase.firestore
        val usersRef = db.collection("users")
        response.value = DataState.Loading
        usersRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documents = task.result?.documents
                documents?.forEach { documentSnapshot ->
                    val username = documentSnapshot.getString("username")
                    if (username != null) {
                        list.add(User(username))
                        Log.d(TAG, "Username: $username")
                    }
                }
                response.value = DataState.Success(list)
            } else {
                Log.d(TAG, "Failed with ", task.exception)
                response.value = DataState.Failure("opsies")
            }
        }
    }
}