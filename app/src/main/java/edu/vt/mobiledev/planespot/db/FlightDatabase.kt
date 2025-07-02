package edu.vt.mobiledev.planespot.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.vt.mobiledev.planespot.ui.component.FlightCard

@Database(entities = [FlightCard::class ], version=1)
@TypeConverters(FlightTypeConverters::class)
abstract class FlightDatabase : RoomDatabase() {
    abstract fun flightDao(): FlightDao
}