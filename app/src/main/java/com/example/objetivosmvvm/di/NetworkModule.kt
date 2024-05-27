package com.example.objetivosmvvm.di

import com.example.objetivosmvvm.model.CountriesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val BASE_URL = "https://raw.githubusercontent.com/"
    @Provides//Retrofit: clase de terceros
    @Singleton
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides//Countries service: interface
    @Singleton
    fun provideCountriesService(retrofit: Retrofit): CountriesService{
        return retrofit.create(CountriesService::class.java)
    }
}