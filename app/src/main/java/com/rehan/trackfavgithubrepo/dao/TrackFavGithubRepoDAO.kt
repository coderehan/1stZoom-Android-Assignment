package com.rehan.trackfavgithubrepo.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rehan.trackfavgithubrepo.models.TrackFavGithubRepoResponse

@Dao
interface TrackFavGithubRepoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trackFavGithubRepoResponse: TrackFavGithubRepoResponse)

    @Query("SELECT * FROM favGithubRepo")
    fun getAllFavGithubRepoData(): LiveData<List<TrackFavGithubRepoResponse>>
}