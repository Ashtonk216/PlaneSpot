package edu.vt.mobiledev.planespot.db

import android.content.Context
import edu.vt.mobiledev.planespot.ui.component.FlightCard
import java.util.UUID

class FlightRepository private constructor(context: Context) {

    private val database: FlightDatabase = FlightDatabase.getDatabase(context)

    //Method that gets all flights from the database
    suspend fun getAllFlights(): List<FlightCard> {
        val results = database.flightDao().getFlights()
        return results
    }

    //Method that gets a specific flight from the database
    suspend fun getFlight(id: UUID): FlightCard? = database.flightDao().getFlight(id)

    //Method that adds a flight to the database
    suspend fun addFlight(flight: FlightCard){
        try {
            database.flightDao().addFlight(flight)
        } catch (e: Exception) {
        }
    }

    //Method that deletes a flight from the database
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