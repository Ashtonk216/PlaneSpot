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
import edu.vt.mobiledev.planespot.databinding.ActivityBasicFlightBinding
import edu.vt.mobiledev.planespot.ui.component.FlightCard
import java.util.Date
import java.util.UUID
class BasicFlightActivity : AppCompatActivity() {
    //Binding and Model
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

        //if the model already has a flight, render it. If not, get it from the intent
        if (basicFlightModel.currentFlightData == null) {
            val flight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("flightData", FlightItem::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra("flightData")
            }
            basicFlightModel.currentFlightData = flight
        }

        //if the flight is null, finish the activity
        if (basicFlightModel.currentFlightData == null) {
            Toast.makeText(this, "Error loading flight data", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        //Render the data from the flight
        renderData()

        //The save button adds the intent to save a flight to the database. Also Finishes the activity
        binding.saveButton.setOnClickListener {
            val flight = basicFlightModel.currentFlightData
            Toast.makeText(this, "Flight saved", Toast.LENGTH_SHORT).show()
            val flightCard = FlightCard(
                date = Date(),
                altitude = flight?.altitude,
                flight = flight?.flight,
                groundSpeed = flight?.groundSpeed,
                lat = flight?.lat,
                lon = flight?.lon,
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
        if (!basicFlightModel.saveButtonEnabled) {
            binding.saveButton.isEnabled = false
            return
        }
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