package edu.vt.mobiledev.planespot.db

import android.content.Context
import edu.vt.mobiledev.planespot.ui.component.FlightCard
import java.util.UUID
import android.util.Log

class FlightRepository private constructor(context: Context) {

    private val database: FlightDatabase = FlightDatabase.getDatabase(context)

    suspend fun getAllFlights(): List<FlightCard> {
        Log.d("Database", "Getting all flights")
        Log.d("Database", "Flights: $database")
        val results = database.flightDao().getFlights() // Remove .value
        Log.d("Database", "Got ${results.size} flights")
        return results
    }

    suspend fun getFlight(id: UUID): FlightCard? = database.flightDao().getFlight(id)

    suspend fun addFlight(flight: FlightCard){
        try {
            database.flightDao().addFlight(flight)
            Log.d("Database", "Flights: $database")
            Log.d("Database", "Flight added successfully: ${flight.id}")
        } catch (e: Exception) {
            Log.e("Database", "Error adding flight: ${e.message}")
        }
    }

    suspend fun deleteFlight(flight: FlightCard) = database.flightDao().deleteFlight(flight)

    companion object {
        private var INSTANCE: FlightRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = FlightRepository(context)
            }
        }

        fun get(): FlightRepository {
            return INSTANCE ?:
            throw IllegalStateException("FlightRepository must be initialized")
        }
    }
}