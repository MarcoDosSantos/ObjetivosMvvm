package com.example.objetivosmvvm.model

import javax.inject.Inject

class CountriesRepository @Inject constructor(private val service : CountriesService) {
    suspend fun getCountries() = service.getCountries()
}