package com.example.gymbros

data class User(
    //var photo: String? = null,
    var id: String? = null,
    var username: String? = null,
    //var location: String? = null,
    var preference: String? = null,
    var bio: String? = null,
    //var friends: MutableList<String>? = null,
)

data class Workout(
    var id: String? = null,
    var type: String? = null,
    var description: String? = null,
    var date: String? = null,
    var duration: String? = null,
    var participants: MutableList<String>? = null,
)
