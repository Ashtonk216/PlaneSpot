package edu.vt.mobiledev.planespot.ui.saved

import androidx.lifecycle.ViewModel
import edu.vt.mobiledev.planespot.db.FlightRepository
import edu.vt.mobiledev.planespot.ui.component.FlightCard


class SavedViewModel : ViewModel() {
    private val flightRepository = FlightRepository.get()

    val flights = mutableListOf<FlightCard>()

    suspend fun loadFlights(): List<FlightCard> {
        return flightRepository.getAllFlights()
    }

    suspend fun deleteFlight(flight: FlightCard) {
        flightRepository.deleteFlight(flight)
    }

    suspend fun addFlight(flight: FlightCard) {
        flightRepository.addFlight(flight)
    }

}