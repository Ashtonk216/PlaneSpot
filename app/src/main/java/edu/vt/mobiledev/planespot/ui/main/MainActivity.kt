package edu.vt.mobiledev.planespot.ui.main

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import edu.vt.mobiledev.planespot.R
import edu.vt.mobiledev.planespot.databinding.ActivityMainBinding
import edu.vt.mobiledev.planespot.db.FlightRepository


class MainActivity : AppCompatActivity() {

    //Name: Ashton Klein
    //PID: ashtonk216

    private lateinit var binding: ActivityMainBinding

    //Setup for the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Inflate layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //safely get the NavController
        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?.findNavController() ?: throw IllegalStateException("NavController not found")

        // Hook up bottom nav with navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }

}

class FlightTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FlightRepository.initialize(this)
    }
}