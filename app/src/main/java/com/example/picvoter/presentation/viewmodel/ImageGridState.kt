package com.example.picvoter.presentation.viewmodel

import com.example.picvoter.domain.model.ImageSearchResult

data class ImageGridState(
    val imageSearchResult: ImageSearchResult? = null,
    val isLoading: Boolean = false
)
