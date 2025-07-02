package edu.vt.mobiledev.planespot.api

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


//This class creates flight items. Flight items are different than FlightCards. FlightItems
//Are defined and parsed by moshi from the json given by the api. When the screen after a flight is
//initially found is displayed it is being displayed as a FlightItem. When the flight is saved
//it is being displayed as a FlightCard.
@Parcelize
@JsonClass(generateAdapter = true)
data class FlightItem(
    @Json(name = "aircraft_name") val aircraftName: String? = null,
    @Json(name = "aircraft_type") val aircraftType: String? = null,
    val airline: String? = null,
    val altitude: Int? = null,
    val destination: String? = null,
    val flight: String? = null,
    @Json(name = "ground_speed") val groundSpeed: Int? = null,
    @Json(name = "info_level") val infoLevel: String,
    val lat: Double? = null,
    val lon: Double? = null,
    val origin: String? = null,
    val status: String? = null
): Parcelable