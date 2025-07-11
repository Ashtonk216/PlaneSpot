package edu.vt.mobiledev.planespot.ui.detail

import androidx.lifecycle.ViewModel
import edu.vt.mobiledev.planespot.api.FlightItem

class BasicFlightViewModel : ViewModel() {
    var currentFlightData: FlightItem? = null
    var saveButtonEnabled = true
}