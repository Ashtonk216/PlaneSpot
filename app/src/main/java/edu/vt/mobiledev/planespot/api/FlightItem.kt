package edu.vt.mobiledev.planespot.api

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


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