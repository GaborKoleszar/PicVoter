package com.example.picvoter.di

import com.example.picvoter.core.Constants
import com.example.picvoter.data.PexelsApi
import com.example.picvoter.data.repository.ImageRepositoryImpl
import com.example.picvoter.domain.repository.ImageRepository
import com.example.picvoter.domain.usecase.SearchForImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providePexelsApi(client: OkHttpClient): PexelsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PexelsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImageRepository(
        api: PexelsApi
    ): ImageRepository {
        return ImageRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSearchForImageUseCase(repository: ImageRepository): SearchForImageUseCase {
        return SearchForImageUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor = interceptor)
        return httpClient.build()
    }
}