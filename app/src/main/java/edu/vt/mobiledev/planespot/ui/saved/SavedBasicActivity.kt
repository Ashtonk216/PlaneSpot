package edu.vt.mobiledev.planespot.ui.saved

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
import edu.vt.mobiledev.planespot.databinding.ActivitySavedBasicFlightBinding
import edu.vt.mobiledev.planespot.ui.component.FlightCard
import edu.vt.mobiledev.planespot.ui.detail.BasicFlightViewModel
import kotlin.getValue

class SavedBasicFlightActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedBasicFlightBinding
    private val savedBasicModel: SavedBasicModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySavedBasicFlightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedBasicModel.currentFlightData == null) {
            val flight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("flightData", FlightCard::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra("flightData")
            }
            savedBasicModel.currentFlightData = flight
        }

        if (savedBasicModel.currentFlightData == null) {
            Toast.makeText(this, "Error loading flight data", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.deleteButton.setOnClickListener {
            Toast.makeText(this, "Flight deleted", Toast.LENGTH_SHORT).show()
            val resultIntent = Intent().apply {
                putExtra("wasDeleted", true)
                putExtra("flightData", savedBasicModel.currentFlightData)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        renderData()

    }

    private fun renderData() {
        if (!savedBasicModel.deleteButtonEnabled) {
            binding.deleteButton.isEnabled = false
            return
        }
        val flight = savedBasicModel.currentFlightData
        binding.flightNumber.text = flight?.flight
        binding.flightSpeed.text = flight?.groundSpeed?.let {
            getString(R.string.flight_speed, it)
        } ?: getString(R.string.flight_speed_unknown)
        binding.flightAltitude.text = flight?.altitude?.let {
            getString(R.string.flight_altitude, it)
        } ?: getString(R.string.flight_altitude_unknown)
    }

}