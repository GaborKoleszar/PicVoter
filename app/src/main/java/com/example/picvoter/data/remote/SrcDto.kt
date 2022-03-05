package com.example.picvoter.data.remote


import android.util.Log
import com.example.picvoter.core.Constants
import com.example.picvoter.domain.model.Src
import com.google.gson.annotations.SerializedName

data class SrcDto(
    @SerializedName("landscape")
    val landscape: String,
    @SerializedName("large")
    val large: String,
    @SerializedName("large2x")
    val large2x: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("original")
    val original: String,
    @SerializedName("portrait")
    val portrait: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("tiny")
    val tiny: String
) {
    fun toSrc(): Src {
        return Src(
            landscape = landscape,
            large = large,
            large2x = large2x,
            medium = medium,
            original = original,
            portrait = portrait,
            small = small,
            tiny = tiny
        )
    }
}