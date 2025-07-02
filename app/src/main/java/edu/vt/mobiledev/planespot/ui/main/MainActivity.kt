package edu.vt.mobiledev.planespot.ui.main

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.location.LocationServices
import edu.vt.mobiledev.planespot.ui.detail.BasicFlightActivity
import edu.vt.mobiledev.planespot.ui.detail.EnrichedFlightActivity
import edu.vt.mobiledev.planespot.R
import edu.vt.mobiledev.planespot.databinding.ActivityMainBinding
import edu.vt.mobiledev.planespot.db.FlightRepository
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    //Name: Ashton Klein
    //PID: ashtonk216

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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