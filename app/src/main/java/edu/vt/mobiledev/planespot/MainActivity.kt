package edu.vt.mobiledev.planespot

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.getValue
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import edu.vt.mobiledev.planespot.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    //Name: Ashton Klein
    //PID: ashtonk216

    private lateinit var binding: ActivityMainBinding
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(edu.vt.mobiledev.planespot.TAG, "onCreate(Bundle?) called")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Getting Location Services Permission if they have not been accepted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getLastKnownLocation()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Setting the find plane button and the api call so if the button is pressed the API is called
        binding.findPlaneButton.setOnClickListener {
            mainViewModel.setWaitingState(true)
            renderView()

            //Calling API
            lifecycleScope.launch {
                val location = mainViewModel.getLocation()
                var lat = location.first
                var lon = location.second
                Log.d(TAG, "Location: $lat, $lon")
                val response = mainViewModel.getFlightData(lat!!, lon!!)
                Log.d(TAG, "Response: $response")
            }
        }



        renderView()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            getLastKnownLocation()
        }
    }


    private fun getLastKnownLocation() {
        //Checking the permission just in case it was not given
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val lat = location.latitude
                    val lon = location.longitude
                    Log.d(TAG, "Got location: $lat, $lon")
                    mainViewModel.setLocation(lat, lon)
                } else {
                    Log.e(TAG, "Location is null")
                }
            }
        } else {
            Log.w(TAG, "Location permission not granted in getLastKnownLocation()")
        }
    }


    private fun renderView(){

        //Render the Find Plane Button Based on the Status of the Find
        binding.findPlaneButton.isEnabled = !mainViewModel.getButtonWaiting()
        binding.findPlaneButton.text = if (mainViewModel.getButtonWaiting())
            getString(R.string.find_button_loading) else getString(R.string.find_button)


    }
}