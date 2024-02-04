package com.example.gymbros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymbros.screens.EditProfile
import com.example.gymbros.screens.Friends
import com.example.gymbros.screens.HomePage
import com.example.gymbros.screens.Match
import com.example.gymbros.screens.Profile
import com.example.gymbros.screens.WorkoutRegister
import com.example.gymbros.viewModels.DatabaseViewModel
import com.google.android.gms.location.FusedLocationProviderClient

class UserActivity : ComponentActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val databaseViewModel = DatabaseViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "home") {
                composable("home") { HomePage(navController, databaseViewModel) }
                composable("profile") { Profile(navController, databaseViewModel) }
                composable("match") { Match(navController, databaseViewModel) }
                composable("friends") { Friends(navController, databaseViewModel) }
                composable("workoutRegister") { WorkoutRegister(navController, databaseViewModel) }
                composable("editProfile") { EditProfile(navController, databaseViewModel) }
            }
        }
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
//                LOCATION_REQUEST_CODE
//            )
//            return
//        }
//        fusedLocationProviderClient.lastLocation
//            .addOnSuccessListener { location: Location? ->
//                if (location != null) {
//                    databaseViewModel.updateLocation(location)
//                }
//            }
//
//    }
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        when (requestCode) {
//            LOCATION_REQUEST_CODE -> {
//                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    // Permission was granted
//                    fusedLocationProviderClient.lastLocation
//                        .addOnSuccessListener { location: Location? ->
//                            if (location != null) {
//                                databaseViewModel.updateLocation(location)
//                            }
//                        }
//                } else {
//                    // Permission was denied
//                    // Handle the case where the user denied the permission
//                }
//                return
//            }
//            else -> {
//                // Ignore other requests
//            }
//        }
    }
}