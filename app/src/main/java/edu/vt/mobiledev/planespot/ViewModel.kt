package edu.vt.mobiledev.planespot

import android.util.Log
import androidx.lifecycle.ViewModel
import edu.vt.mobiledev.planespot.api.FlightItem
import edu.vt.mobiledev.planespot.api.PlaneSpotApi
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainViewModel : ViewModel() {

    private var isWaiting: Boolean = false
    private var isFlightFoundError: Boolean = false
    private var isServerError: Boolean = false
    private var currentLat: Double? = null
    private var currentLon: Double? = null
    private var currentFlightData: FlightItem? = null

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

    fun getLocation(): Pair<Double?, Double?> {
        return Pair(currentLat, currentLon)
    }

    suspend fun getFlightData(): FlightItem {
        val lat = currentLat
        val lon = currentLon
        if (lat == null || lon == null) {
            Log.e("MainViewModel", "Location is null! Cannot fetch flight.")
            throw IllegalStateException("Location not set before fetching flight data")
        }
        Log.d("MainViewModel", "Fetching flight data for: $lat, $lon")
        val flight = FlightRepository.getFlightData(lat, lon)
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
