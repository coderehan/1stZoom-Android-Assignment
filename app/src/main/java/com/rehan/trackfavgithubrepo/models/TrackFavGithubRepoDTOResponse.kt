package com.rehan.trackfavgithubrepo.models

// This data class is for API
data class TrackFavGithubRepoDTOResponse(
    val id: String,
    val html_url: String?,          // Repository Link
    val language: String?,          // Repository Language
    val name: String?,              // Repository Name
    val owner: Owner                // Repository Owner Info
)
