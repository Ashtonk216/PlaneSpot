package edu.vt.mobiledev.planespot.ui.detail

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.vt.mobiledev.planespot.ui.detail.BasicFlightViewModel
import edu.vt.mobiledev.planespot.R
import edu.vt.mobiledev.planespot.api.FlightItem
import edu.vt.mobiledev.planespot.databinding.ActivityBasicFlightBinding

class BasicFlightActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBasicFlightBinding
    private val basicFlightModel: BasicFlightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBasicFlightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get flight from intent
        val flight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("flightData", FlightItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("flightData")
        }

        if (flight == null) {
            Toast.makeText(this, "Error loading flight data", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        basicFlightModel.currentFlightData = flight

        renderData()

        binding.saveButton.setOnClickListener {
            Toast.makeText(this, "Flight saved (not implemented)", Toast.LENGTH_SHORT).show()
            // TODO: implement saving flight to disk/database
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun renderData() {
        val flight = basicFlightModel.currentFlightData
        binding.flightNumber.text = flight?.flight
        binding.flightSpeed.text = flight?.groundSpeed?.let {
            getString(R.string.flight_speed, it)
        } ?: getString(R.string.flight_speed_unknown)
        binding.flightAltitude.text = flight?.altitude?.let {
            getString(R.string.flight_altitude, it)
        } ?: getString(R.string.flight_altitude_unknown)
    }
}