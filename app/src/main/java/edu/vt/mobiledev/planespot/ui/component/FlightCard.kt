package edu.vt.mobiledev.planespot.ui.component

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID
import kotlinx.parcelize.Parcelize

@Entity(tableName = "flights")
@Parcelize
data class FlightCard(
    @PrimaryKey val id: UUID,
    val date: Date,
    val airline: String? = null,
    val altitude: Int? = null,
    val aircraftName: String? = null,
    val destination: String? = null,
    val flight: String? = null,
    val groundSpeed: Int? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val origin: String? = null
) : Parcelable