package edu.vt.mobiledev.planespot.api

import retrofit2.http.GET
import retrofit2.http.Path

//Defining the api call
interface PlaneSpotApi {
    @GET("get-flight/lat/{lat}/lon/{lon}")
    suspend fun getFlightData(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double
    ): FlightItem
}