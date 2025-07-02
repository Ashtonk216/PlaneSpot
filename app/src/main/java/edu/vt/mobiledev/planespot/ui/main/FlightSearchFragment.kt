package edu.vt.mobiledev.planespot.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import edu.vt.mobiledev.planespot.R
import edu.vt.mobiledev.planespot.api.FlightItem
import edu.vt.mobiledev.planespot.databinding.FragmentFlightSearchBinding
import edu.vt.mobiledev.planespot.ui.component.FlightCard
import edu.vt.mobiledev.planespot.ui.detail.BasicFlightActivity
import edu.vt.mobiledev.planespot.ui.detail.EnrichedFlightActivity
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class FlightSearchFragment : Fragment() {


    private var _binding: FragmentFlightSearchBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private val flightDetailsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val wasSaved = result.data?.getBooleanExtra("wasSaved", false) == true
                if (wasSaved) {
                    val flight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        result.data?.getParcelableExtra("flightData", FlightCard::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        result.data?.getParcelableExtra("flightData")
                    }
                    flight?.let {
                        viewLifecycleOwner.lifecycleScope.launch {
                            Log.d(TAG, "Saving flight to database: $it")
                            mainViewModel.addFlight(it)
                        }
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlightSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.d(TAG, "Permission granted")
                getLastKnownLocation()
            } else {
                Log.w(TAG, "Permission denied")
            }
        }

        binding.findPlaneButton.setOnClickListener {
            mainViewModel.setWaitingState(true)
            renderView()

            //Calling API
            Log.d(TAG, "API call started")
            lifecycleScope.launch {
                fetchFlightAndNavigate()
            }
        }

        renderView()
        getLastKnownLocation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
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

    private fun navigateToFlightDetails() {
        val flight = mainViewModel.getCurrentFlightData() ?: return
        val intent = when (flight.infoLevel) {
            "enriched" -> Intent(requireContext(), EnrichedFlightActivity::class.java)
            "basic" -> Intent(requireContext(), BasicFlightActivity::class.java)
            else -> null
        }?.apply {
            putExtra("flightData", flight)
        }

        intent?.let {
            flightDetailsLauncher.launch(it)
        }
    }


    //renders the view for the main page based on user action
    private fun renderView() {
        val errorColor = ContextCompat.getColorStateList(requireContext(), R.color.colorError)
        val normalColor = ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)

        if (mainViewModel.getFlightFoundError()) {
            binding.findPlaneButton.apply {
                isEnabled = true
                text = getString(R.string.flight_not_found)
                backgroundTintList = errorColor
            }
            return
        }

        if (mainViewModel.getServerError()) {
            binding.findPlaneButton.apply {
                isEnabled = true
                text = getString(R.string.server_error)
                backgroundTintList = errorColor
            }
            return
        }

        binding.findPlaneButton.apply {
            isEnabled = !mainViewModel.getButtonWaiting()
            text = if (mainViewModel.getButtonWaiting())
                getString(R.string.find_button_loading)
            else
                getString(R.string.find_button)
            backgroundTintList = normalColor
        }
    }

    //Fetches the flight data from the API and navigates to the flight details page
    private fun fetchFlightAndNavigate() {
        lifecycleScope.launch {
            try {
                Log.d(TAG, "fetchFlightAndNavigate()")
                val flight = mainViewModel.getFlightData()
                if (flight.infoLevel == "not_found") {
                    mainViewModel.setFlightFoundError(true)
                    renderView()
                } else {
                    navigateToFlightDetails() // don't reset here, we're leaving
                }
            } catch (e: Exception) {
                Log.e(TAG, "API call failed", e)
                mainViewModel.setServerError(true)
                renderView()
            }
        }
    }

    //Used to reset the button to the default state after the user has returned to the page
    override fun onResume() {
        super.onResume()
        mainViewModel.setWaitingState(false)
        renderView()
    }
}