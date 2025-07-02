package edu.vt.mobiledev.planespot.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.vt.mobiledev.planespot.ui.component.FlightCard
import java.util.UUID

//FlightDao for Room Database
@Dao
interface FlightDao {
    @Query("SELECT * FROM flights")
    suspend fun getFlights(): List<FlightCard>

    @Query("SELECT * FROM flights WHERE id = :id")
    suspend fun getFlight(id: UUID): FlightCard?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFlight(flight: FlightCard)

    @Delete
    suspend fun deleteFlight(flight: FlightCard)
}