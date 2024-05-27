package com.example.objetivosmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.objetivosmvvm.model.CountriesRepository
import com.example.objetivosmvvm.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(private val repository : CountriesRepository) : ViewModel() {

    private val _countries = MutableStateFlow<List<Country>?>(emptyList())
    val countries: StateFlow<List<Country>?> = _countries

    private val _countryLoadError = MutableStateFlow<Boolean?>(null)
    val countryLoadError: StateFlow<Boolean?> = _countryLoadError

    private val _loading = MutableStateFlow<Boolean?>(null)
    val loading: StateFlow<Boolean?> = _loading

    fun refresh() {
        viewModelScope.launch {
            fetchData()
        }
    }

    private suspend fun fetchData() {
        try {
            _loading.value = true
            val response = repository.getCountries()
            _countryLoadError.value = false
            _loading.value = false
            if (response.isSuccessful){
                _countries.value = response.body()
            }

        } catch (e: Exception) {
            Log.e("MyTag", e.message.toString())
            _countryLoadError.value = true
            _loading.value = false
        }
    }
}
