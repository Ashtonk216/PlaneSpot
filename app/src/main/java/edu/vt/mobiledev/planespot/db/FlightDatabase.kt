package edu.vt.mobiledev.planespot.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Room
import edu.vt.mobiledev.planespot.ui.component.FlightCard

//FlightDatabase for Room Database
@Database(
    entities = [FlightCard::class],
    version = 1,
    exportSchema = false
)
//FlightTypeConverters for Room Database that convert dates and UUIDs to strings
@TypeConverters(FlightTypeConverters::class)
abstract class FlightDatabase : RoomDatabase() {

    abstract fun flightDao(): FlightDao

    companion object {
        @Volatile
        private var INSTANCE: FlightDatabase? = null

        const val DATABASE_NAME = "flight_database"

        fun getDatabase(context: Context): FlightDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlightDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}