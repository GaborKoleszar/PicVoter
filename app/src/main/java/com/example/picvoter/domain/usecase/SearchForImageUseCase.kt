package com.example.picvoter.domain.usecase

import com.example.picvoter.core.Resource
import com.example.picvoter.domain.model.ImageSearchResult
import com.example.picvoter.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchForImageUseCase(
    private val repository: ImageRepository
) {
    operator fun invoke(
        searchQuery: String,
        numberOfImagesRequested: Int
    ): Flow<Resource<ImageSearchResult>> {
        return if (searchQuery.isBlank())
            flow {}
        else
            repository.getImageBySearch(searchQuery, numberOfImagesRequested)
    }
}