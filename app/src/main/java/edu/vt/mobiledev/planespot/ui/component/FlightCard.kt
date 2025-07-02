package edu.vt.mobiledev.planespot.ui.component

import androidx.lifecycle.MutableLiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID
import androidx.room.TypeConverter

@Entity(tableName = "flights")
data class FlightCard(
    @PrimaryKey val id: UUID,
    val date: Date,
    val airline: String? = null,
    val altitude: Int? = null,
    val destination: String? = null,
    val flight: String? = null,
    val groundSpeed: Int? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val origin: String? = null
)