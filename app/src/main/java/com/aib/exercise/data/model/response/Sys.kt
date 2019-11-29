package com.aib.exercise.data.model.response


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Sys(
    @SerializedName("pod")
    val pod: String
)