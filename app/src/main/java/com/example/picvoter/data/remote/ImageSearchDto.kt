package com.example.picvoter.data.remote


import android.util.Log
import com.example.picvoter.core.Constants
import com.example.picvoter.domain.model.ImageSearchResult
import com.google.gson.annotations.SerializedName

data class ImageSearchDto(
    @SerializedName("next_page")
    val nextPage: String,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("photos")
    val photos: List<PhotoDto>,
    @SerializedName("total_results")
    val totalResults: Int
) {
    fun toImageSearchResult(): ImageSearchResult {
        return ImageSearchResult(
            nextPage = nextPage,
            page = page,
            perPage = perPage,
            photos = photos.map { it.toPhoto() },
            totalResults = totalResults
        )
    }
}