package com.example.mobile_development_lab_09.api.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse (
    @SerializedName("Response") val response: String,
    @SerializedName("Error") val error: String,
)