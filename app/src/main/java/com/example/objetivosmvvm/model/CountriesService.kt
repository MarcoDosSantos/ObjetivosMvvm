package com.example.objetivosmvvm.model


import retrofit2.Response
import retrofit2.http.GET

interface CountriesService {
    @GET("DevTides/countries/master/countriesV2.json")
    suspend fun getCountries(): Response<CountriesResponse>
}