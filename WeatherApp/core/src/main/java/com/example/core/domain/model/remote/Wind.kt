package com.example.core.domain.model.remote

import com.google.gson.annotations.SerializedName

   
data class Wind (

   @SerializedName("speed") var speed : Double,
   @SerializedName("deg") var deg : Int

)