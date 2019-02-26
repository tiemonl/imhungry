package io.imhungry.GoogleAPI


object initiateGoogleAPIService {
    private val GOOGLE_API_URL = "https://maps.googleapis.com/"

    val googleAPIService:IGoogleAPIService
        get() = RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService::class.java)
}