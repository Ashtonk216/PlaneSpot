package edu.vt.mobiledev.planespot.api

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


//Flight Bank for Api Usage
object FlightBank {
    private val moshi = Moshi.Builder().build()

    private val retrofit: Retrofit = Retrofit.Builder()
        //Calls personal server with api set up. If there are issues there could be rate limits
        .baseUrl("https://ashtonashton.net/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    //Holding api instance
    val flightApi: PlaneSpotApi = retrofit.create(PlaneSpotApi::class.java)

    //Gets flight data from api
    suspend fun getFlightData(lat: Double, lon: Double): FlightItem {
        return flightApi.getFlightData(lat, lon)
    }
}