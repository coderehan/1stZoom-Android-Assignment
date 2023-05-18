package com.rehan.trackfavgithubrepo.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rehan.trackfavgithubrepo.api.TrackFavGithubRepoAPI
import com.rehan.trackfavgithubrepo.database.TrackFavGithubRepoDatabase
import com.rehan.trackfavgithubrepo.models.TrackFavGithubRepoDTOResponse
import com.rehan.trackfavgithubrepo.models.TrackFavGithubRepoResponse
import com.rehan.trackfavgithubrepo.utils.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

class TrackFavGithubRepoRepository @Inject constructor(private val trackFavGithubRepoAPI: TrackFavGithubRepoAPI) {

    // API
    private val _favGithubRepoResponseMutableLiveData = MutableLiveData<NetworkResult<TrackFavGithubRepoDTOResponse>>()
    val favGithubRepoResponseLiveData: LiveData<NetworkResult<TrackFavGithubRepoDTOResponse>>
        get() = _favGithubRepoResponseMutableLiveData

    suspend fun getFavGithubRepoList(repoOwnerName: String, repoName: String) {
        try {
            _favGithubRepoResponseMutableLiveData.postValue(NetworkResult.Loading())
            val response = trackFavGithubRepoAPI.getFavGithubRepoList(repoOwnerName, repoName)
            if (response.isSuccessful && response.body() != null) {
                Log.d("FavGithubRepoAPI", response.message())
                _favGithubRepoResponseMutableLiveData.postValue(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObject = JSONObject(response.errorBody()!!.charStream().readText())
                val error = errorObject.getString("message")        // This "message" is the actual json key name for error response in postman
                _favGithubRepoResponseMutableLiveData.postValue(NetworkResult.Error(error))
            } else {
                _favGithubRepoResponseMutableLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }
        } catch (exception: Exception) {
            Log.d("ExceptionFavGithubRepo", "Exception ${exception.message}")
        }
    }


    // Database
    companion object{
        private var trackFavGithubRepoDatabase: TrackFavGithubRepoDatabase? = null

        private fun initializeDB(context: Context) : TrackFavGithubRepoDatabase?{
            return TrackFavGithubRepoDatabase.getInstance(context)
        }

        fun insert(context: Context, trackFavGithubRepoResponse: TrackFavGithubRepoResponse){
            trackFavGithubRepoDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                trackFavGithubRepoDatabase?.getDao()?.insert(trackFavGithubRepoResponse)
            }
        }

        fun getAllFavGithubRepoData(context: Context): LiveData<List<TrackFavGithubRepoResponse>>?{
            trackFavGithubRepoDatabase = initializeDB(context)
            return trackFavGithubRepoDatabase?.getDao()?.getAllFavGithubRepoData()
        }
    }
}