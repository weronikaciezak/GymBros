package com.example.gymbros

sealed class DataState {
    class Success(val data: MutableList<User>) : DataState()
    //class Succes(val data: String) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}