package edu.vt.mobiledev.planespot.ui.detail

import androidx.lifecycle.ViewModel
import edu.vt.mobiledev.planespot.api.FlightItem

class EnrichedFlightViewModel : ViewModel() {
    var currentFlightData: FlightItem? = null
    var saveButtonEanbled = true
}