package io.imhungry.GoogleAPI


import io.imhungry.model.RootObject
import retrofit2.http.GET
import retrofit2.http.Url
import retrofit2.Call

interface IGoogleAPIService {
    @GET
    fun getNearbyPlaces(@Url url:String):Call<RootObject>
}