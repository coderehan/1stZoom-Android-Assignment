package com.rehan.trackfavgithubrepo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rehan.trackfavgithubrepo.dao.TrackFavGithubRepoDAO
import com.rehan.trackfavgithubrepo.models.TrackFavGithubRepoResponse

@Database(entities = [TrackFavGithubRepoResponse::class], version = 1, exportSchema = false)
abstract class TrackFavGithubRepoDatabase: RoomDatabase() {

    abstract fun getDao(): TrackFavGithubRepoDAO

    companion object {
        private const val DATABASE_NAME = "TrackFavGithubRepo"

        @Volatile
        var trackFavGithubRepoDatabase: TrackFavGithubRepoDatabase? = null

        // Creating function for database
        fun getInstance(context: Context): TrackFavGithubRepoDatabase? {

            if (trackFavGithubRepoDatabase == null) {
                synchronized(TrackFavGithubRepoDatabase::class.java) {
                    if (trackFavGithubRepoDatabase == null) {
                        trackFavGithubRepoDatabase = Room.databaseBuilder(context, TrackFavGithubRepoDatabase::class.java, DATABASE_NAME).build()
                    }
                }
            }
            return trackFavGithubRepoDatabase
        }
    }
}