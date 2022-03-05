package com.example.picvoter.domain.repository

import com.example.picvoter.core.Resource
import com.example.picvoter.domain.model.ImageSearchResult
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun getImageBySearch(
        searchQuery: String,
        numberOfImagesRequested: Int
    ): Flow<Resource<ImageSearchResult>>
}