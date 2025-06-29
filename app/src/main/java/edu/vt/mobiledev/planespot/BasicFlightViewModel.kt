package edu.vt.mobiledev.planespot

import androidx.lifecycle.ViewModel
import edu.vt.mobiledev.planespot.api.FlightItem

class BasicFlightViewModel : ViewModel() {
    var currentFlightData: FlightItem? = null
}