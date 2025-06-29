package edu.vt.mobiledev.planespot

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.vt.mobiledev.planespot.api.FlightItem
import edu.vt.mobiledev.planespot.databinding.ActivityEnrichedFlightBinding
import android.util.Log
import android.widget.Toast

class EnrichedFlightActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnrichedFlightBinding
    private val enrichedFlightModel: EnrichedFlightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEnrichedFlightBinding.inflate(layoutInflater)
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

        enrichedFlightModel.currentFlightData = flight

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
        val flight = enrichedFlightModel.currentFlightData
        binding.flightNumber.text = flight?.flight
        binding.aircraftName.text = flight?.aircraftName
        binding.airlineName.text = flight?.airline
        binding.origin.text = flight?.origin
        binding.destination.text = flight?.destination
        binding.flightSpeed.text = flight?.groundSpeed?.let {
            getString(R.string.flight_speed, it)
        } ?: getString(R.string.flight_speed_unknown)
        binding.flightAltitude.text = flight?.altitude?.let {
            getString(R.string.flight_altitude, it)
        } ?: getString(R.string.flight_altitude_unknown)
    }
}