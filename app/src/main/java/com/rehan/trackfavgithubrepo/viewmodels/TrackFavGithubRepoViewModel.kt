package com.rehan.trackfavgithubrepo.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehan.trackfavgithubrepo.models.TrackFavGithubRepoDTOResponse
import com.rehan.trackfavgithubrepo.models.TrackFavGithubRepoResponse
import com.rehan.trackfavgithubrepo.repositories.TrackFavGithubRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackFavGithubRepoViewModel @Inject constructor(private val trackFavGithubRepoRepository: TrackFavGithubRepoRepository) : ViewModel() {

    val favGithubRepoResponseLiveData get() = trackFavGithubRepoRepository.favGithubRepoResponseLiveData

    fun getFavGithubRepoList(repoOwnerName: String, repoName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            trackFavGithubRepoRepository.getFavGithubRepoList(repoOwnerName, repoName)
        }
    }

    // This function is used to insert data in room database
    fun insert(context: Context, trackFavGithubRepoDTO: TrackFavGithubRepoDTOResponse){
        val result = TrackFavGithubRepoResponse(
            id = trackFavGithubRepoDTO.id,
            html_url = trackFavGithubRepoDTO.html_url,
            language = trackFavGithubRepoDTO.language,
            name = trackFavGithubRepoDTO.name,
            avatar_url = trackFavGithubRepoDTO.owner.avatar_url,
            login = trackFavGithubRepoDTO.owner.login
        )
        return TrackFavGithubRepoRepository.insert(context, result)
    }

    // This function is used to retrieve data from room database
    fun getAllFavGithubRepoData(context: Context) : LiveData<List<TrackFavGithubRepoResponse>>? {
        return TrackFavGithubRepoRepository.getAllFavGithubRepoData(context)
    }

}