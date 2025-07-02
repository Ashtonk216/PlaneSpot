package edu.vt.mobiledev.planespot.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.vt.mobiledev.planespot.databinding.FragmentFlightEnrichedBinding
import edu.vt.mobiledev.planespot.ui.component.FlightCard
import java.util.Date
import java.util.UUID

class EnrichedFlightFragment: Fragment() {
    private lateinit var flight: FlightCard
    private lateinit var binding: FragmentFlightEnrichedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flight = FlightCard(
            id = UUID.randomUUID(),
            date = Date(),
            airline = TODO(),
            altitude = TODO(),
            destination = TODO(),
            flight = TODO(),
            groundSpeed = TODO(),
            lat = TODO(),
            lon = TODO(),
            origin = TODO(),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentFlightEnrichedBinding.inflate(inflater, container, false)
        return binding.root

        binding.flightNumber.text = flight.flight
        binding.aircraftName.text = flight.airline
        binding.airlineName.text = flight.airline
        binding.origin.text = flight.origin
        binding.destination.text = flight.destination
        binding.flightSpeed.text = flight.groundSpeed.toString()
        binding.flightAltitude.text = flight.altitude.toString()
        binding.deleteButton.setOnClickListener {
            parentFragmentManager.popBackStack()
            binding.deleteButton.apply{
                isEnabled = false
            }
        }
        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()

        }
    }

}