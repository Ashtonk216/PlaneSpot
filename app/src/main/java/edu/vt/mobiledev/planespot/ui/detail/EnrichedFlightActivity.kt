package edu.vt.mobiledev.planespot.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.vt.mobiledev.planespot.R
import edu.vt.mobiledev.planespot.api.FlightItem
import edu.vt.mobiledev.planespot.databinding.ActivityEnrichedFlightBinding
import edu.vt.mobiledev.planespot.ui.component.FlightCard
import java.util.Date
import java.util.UUID
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
        if (enrichedFlightModel.currentFlightData == null) {
            val flight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("flightData", FlightItem::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra("flightData")
            }
            enrichedFlightModel.currentFlightData = flight
        }

        if (enrichedFlightModel.currentFlightData == null) {
            Toast.makeText(this, "Error loading flight data", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        renderData()

        // Save button click listener that tells the parent activity to save the flight
        binding.saveButton.setOnClickListener {
            Toast.makeText(this, "Flight saved", Toast.LENGTH_SHORT).show()
            val flight = enrichedFlightModel.currentFlightData
            val flightCard = FlightCard(
                date = Date(),
                airline = flight?.airline,
                aircraftName = flight?.aircraftName,
                altitude = flight?.altitude,
                destination = flight?.destination,
                flight = flight?.flight,
                groundSpeed = flight?.groundSpeed,
                lat = flight?.lat,
                lon = flight?.lon,
                origin = flight?.origin,
                id = UUID.randomUUID()
            )
            val resultIntent = Intent().apply {
                putExtra("flightData", flightCard)
                putExtra("wasSaved", true)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun renderData() {
        if (!enrichedFlightModel.saveButtonEanbled) {
            binding.saveButton.isEnabled = false
            return
        }
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