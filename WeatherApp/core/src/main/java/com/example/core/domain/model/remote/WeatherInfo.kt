package com.example.core.domain.model.remote

import com.example.core.domain.model.remote.model.Coord
import com.example.core.domain.model.remote.model.Main
import com.example.core.domain.model.remote.model.Sys
import com.google.gson.annotations.SerializedName


data class WeatherInfo<T> (

    @SerializedName("coord") var coord : Coord,
    @SerializedName("weather") var weather : List<T>,
    @SerializedName("base") var base : String,
    @SerializedName("main") var main : Main,
    @SerializedName("visibility") var visibility : Int,
    @SerializedName("wind") var wind : Wind,
    @SerializedName("clouds") var clouds : Clouds,
    @SerializedName("dt") var dt : Int,
    @SerializedName("sys") var sys : Sys,
    @SerializedName("timezone") var timezone : Int,
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("cod") var cod : Int

)