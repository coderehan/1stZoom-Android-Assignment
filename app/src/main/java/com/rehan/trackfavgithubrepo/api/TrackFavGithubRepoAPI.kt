package com.rehan.trackfavgithubrepo.api

import com.rehan.trackfavgithubrepo.models.TrackFavGithubRepoDTOResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TrackFavGithubRepoAPI {

    @GET("/repos/{owner}/{repo}")
    suspend fun getFavGithubRepoList(
        @Path("owner") repoOwnerName: String,
        @Path("repo") repoName: String,
    ): Response<TrackFavGithubRepoDTOResponse>

}