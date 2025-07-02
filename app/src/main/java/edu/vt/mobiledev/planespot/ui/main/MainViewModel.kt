package edu.vt.mobiledev.planespot.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import edu.vt.mobiledev.planespot.db.FlightRepository
import edu.vt.mobiledev.planespot.api.FlightItem
import edu.vt.mobiledev.planespot.ui.component.FlightCard
import edu.vt.mobiledev.planespot.api.FlightBank


class MainViewModel : ViewModel() {

    private var isWaiting: Boolean = false
    private var isFlightFoundError: Boolean = false
    private var isServerError: Boolean = false
    private var currentLat: Double? = null
    private var currentLon: Double? = null
    private var currentFlightData: FlightItem? = null
    private val flightRepository = FlightRepository.get()


    suspend fun addFlight(flight: FlightCard) {
        flightRepository.addFlight(flight)
    }

    fun setWaitingState(waiting: Boolean) {
        isWaiting = waiting
    }

    fun getButtonWaiting(): Boolean{
        return isWaiting
    }

    fun setLocation(lat: Double, lon: Double) {
        currentLat = lat
        currentLon = lon
    }


    suspend fun getFlightData(): FlightItem {
        val lat = currentLat
        val lon = currentLon
        if (lat == null || lon == null) {
            Log.e("MainViewModel", "Location is null! Cannot fetch flight.")
            throw IllegalStateException("Location not set before fetching flight data")
        }
        Log.d("MainViewModel", "Fetching flight data for: $lat, $lon")
        val flight = FlightBank.getFlightData(lat, lon)
        Log.d("MainViewModel", "Received flight: ${flight.flight}, infoLevel: ${flight.infoLevel}")
        currentFlightData = flight
        return flight
    }


    fun getCurrentFlightData(): FlightItem? {
        return currentFlightData
    }

    fun setFlightFoundError(error: Boolean) {
        isFlightFoundError = error
    }

    fun getFlightFoundError(): Boolean {
        return isFlightFoundError
    }

    fun setServerError(error: Boolean) {
        isServerError = error
    }

    fun getServerError(): Boolean {
        return isServerError
    }

}
