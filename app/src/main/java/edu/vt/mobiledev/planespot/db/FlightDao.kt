package edu.vt.mobiledev.planespot.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import edu.vt.mobiledev.planespot.ui.component.FlightCard
import java.util.UUID

@Dao
interface FlightDao {
    @Query("SELECT * FROM flights")
    fun getFlights(): LiveData<List<FlightCard>>

    @Query("SELECT * FROM flights WHERE id=(:id)")
    suspend fun getFlight(id: UUID): FlightCard?

    @Insert
    suspend fun addFlight(flight: FlightCard)

    @Delete
    suspend fun deleteFlight(flight: FlightCard)

}