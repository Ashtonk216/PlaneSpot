package edu.vt.mobiledev.planespot.db

import android.app.Application
import android.content.Context

class FlightRepository private constructor(context: Context) {

    companion object {
        private var INSTANCE: FlightRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = FlightRepository(context)
            }
        }

        fun get(): FlightRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}
