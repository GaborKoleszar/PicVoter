package com.example.picvoter.domain.model

data class ImageSearchResult(
    val nextPage: String,
    val page: Int,
    val perPage: Int,
    val photos: List<Photo>,
    val totalResults: Int
)
