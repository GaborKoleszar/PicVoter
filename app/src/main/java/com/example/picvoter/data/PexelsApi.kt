package com.example.picvoter.data

import com.example.picvoter.data.remote.ImageSearchDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PexelsApi {

    @Headers("Authorization: 563492ad6f9170000100000151336a4205494285baa335d1c54275b9")
    @GET("search")
    suspend fun getImageBySearch(@Query("query") query: String, @Query("per_page") perPage: Int): ImageSearchDto
}