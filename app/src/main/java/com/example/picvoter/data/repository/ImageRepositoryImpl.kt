package com.example.picvoter.data.repository

import android.util.Log
import com.example.picvoter.core.Constants.APP_NAME
import com.example.picvoter.core.Resource
import com.example.picvoter.data.PexelsApi
import com.example.picvoter.domain.model.ImageSearchResult
import com.example.picvoter.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ImageRepositoryImpl(
    private val pexelsApi: PexelsApi
) : ImageRepository {

    override fun getImageBySearch(
        searchQuery: String,
        numberOfImagesRequested: Int
    ): Flow<Resource<ImageSearchResult>> = flow {
        emit(Resource.Loading())
        Log.d(APP_NAME, "Loading...")

        try {
            val response = pexelsApi.getImageBySearch(searchQuery, numberOfImagesRequested)
            emit(Resource.Success(response.toImageSearchResult()))
            Log.d(APP_NAME, "Success...")
        } catch (e: HttpException) {
            emit(Resource.Error(message = "HttpException happened"))
            Log.d(APP_NAME, "Error... ${e.message()}")
        } catch (e: IOException) {
            emit(Resource.Error(message = "IOException happened"))
            Log.d(APP_NAME, "Error... ${e.localizedMessage}")
        } catch (e: Exception) {
            emit(Resource.Error(message = "Fatal error ${e.localizedMessage}"))
            Log.d(APP_NAME, "Error... ${e.localizedMessage}")
        }
    }
}