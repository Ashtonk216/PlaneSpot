package edu.vt.mobiledev.planespot.db

import androidx.room.TypeConverter
import java.util.Date

class FlightTypeConverters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}