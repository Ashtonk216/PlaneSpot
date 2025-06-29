package edu.vt.mobiledev.planespot

import androidx.lifecycle.ViewModel
import edu.vt.mobiledev.planespot.api.FlightItem

class EnrichedFlightViewModel : ViewModel() {
    var currentFlightData: FlightItem? = null
}