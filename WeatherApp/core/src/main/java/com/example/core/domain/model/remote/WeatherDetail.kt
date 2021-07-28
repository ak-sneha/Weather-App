package com.example.core.domain.model.remote

import com.google.gson.annotations.SerializedName

data class WeatherDetail (

   @SerializedName("id") var id : Int,
   @SerializedName("main") var main : String,
   @SerializedName("description") var description : String,
   @SerializedName("icon") var icon : String

)