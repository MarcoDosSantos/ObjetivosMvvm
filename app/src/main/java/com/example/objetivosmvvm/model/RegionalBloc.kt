package com.example.objetivosmvvm.model


import com.google.gson.annotations.SerializedName

data class RegionalBloc(
    @SerializedName("acronym")
    val acronym: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("otherAcronyms")
    val otherAcronyms: List<String>,
    @SerializedName("otherNames")
    val otherNames: List<String>
)