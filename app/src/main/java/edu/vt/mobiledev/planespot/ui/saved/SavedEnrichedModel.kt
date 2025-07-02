package edu.vt.mobiledev.planespot.ui.saved

import androidx.lifecycle.ViewModel
import edu.vt.mobiledev.planespot.ui.component.FlightCard

class SavedEnrichedModel : ViewModel() {
    var currentFlightData: FlightCard? = null
    var deleteButtonEnabled = true
}