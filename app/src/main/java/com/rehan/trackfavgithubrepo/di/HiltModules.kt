package com.rehan.trackfavgithubrepo.di

import com.rehan.trackfavgithubrepo.api.TrackFavGithubRepoAPI
import com.rehan.trackfavgithubrepo.repositories.TrackFavGithubRepoRepository
import com.rehan.trackfavgithubrepo.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HiltModules {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesTrackFavGithubRepoAPI(retrofit: Retrofit): TrackFavGithubRepoAPI {
        return retrofit.create(TrackFavGithubRepoAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesTrackFavGithubRepository(trackFavGithubRepoAPI: TrackFavGithubRepoAPI): TrackFavGithubRepoRepository {
        return TrackFavGithubRepoRepository(trackFavGithubRepoAPI)
    }

}