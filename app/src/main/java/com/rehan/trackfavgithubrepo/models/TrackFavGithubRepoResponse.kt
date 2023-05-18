package com.rehan.trackfavgithubrepo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// This data class is for room database
@Entity(tableName = "favGithubRepo")
data class TrackFavGithubRepoResponse(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val html_url: String?,          // Repository Link
    val language: String?,          // Repository Language
    val name: String?,              // Repository Name
    val avatar_url: String?,        // Repository Owner Image
    val login: String?              // Repository Owner Info
)