package edu.vt.mobiledev.planespot

import com.squareup.moshi.Moshi
import edu.vt.mobiledev.planespot.api.FlightItem
import edu.vt.mobiledev.planespot.api.PlaneSpotApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


//Define as a Singleton. Only one call being made at a time
object FlightRepository {
    private val moshi = Moshi.Builder().build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://ashtonashton.net/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val flightApi: PlaneSpotApi = retrofit.create(PlaneSpotApi::class.java)

    suspend fun getFlightData(lat: Double, lon: Double): FlightItem {
        return flightApi.getFlightData(lat, lon)
    }
}