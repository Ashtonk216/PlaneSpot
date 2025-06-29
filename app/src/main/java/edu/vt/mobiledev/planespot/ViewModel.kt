package edu.vt.mobiledev.planespot

import androidx.lifecycle.ViewModel
import edu.vt.mobiledev.planespot.api.PlaneSpotApi
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainViewModel : ViewModel() {

    var isWaiting: Boolean = false
    private var currentLat: Double? = null
    private var currentLon: Double? = null

    object ApiClient {
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://ashtonashton.net/") // your actual API root
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val flightApi: PlaneSpotApi = retrofit.create(PlaneSpotApi::class.java)
    }


    fun setWaitingState(waiting: Boolean) {
        isWaiting = waiting
    }

    fun getButtonWaiting(): Boolean{
        return isWaiting
    }

    fun setLocation(lat: Double, lon: Double) {
        currentLat = lat
        currentLon = lon
    }

    fun getLocation(): Pair<Double?, Double?> {
        return Pair(currentLat, currentLon)
    }

    suspend fun getFlightData(lat: Double, lon: Double): String {
        return ApiClient.flightApi.getFlightData(lat, lon)
    }

}
