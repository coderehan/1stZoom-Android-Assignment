package com.rehan.trackfavgithubrepo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rehan.trackfavgithubrepo.R
import com.rehan.trackfavgithubrepo.databinding.ActivityAddRepoBinding
import com.rehan.trackfavgithubrepo.utils.NetworkResult
import com.rehan.trackfavgithubrepo.viewmodels.TrackFavGithubRepoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRepoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRepoBinding
    private lateinit var favGithubRepoViewModel: TrackFavGithubRepoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_repo)

        favGithubRepoViewModel = ViewModelProvider(this)[TrackFavGithubRepoViewModel::class.java]

        supportActionBar?.title = resources.getString(R.string.add_repo)
        handleClickListener()

    }

    private fun handleClickListener() {
        binding.btnAddRepo.setOnClickListener {
            handlingValidations()
        }
    }

    private fun handlingValidations() {
        val repoOwnerName = binding.etRepoOwnerName.editableText.toString().trim()
        val repoName = binding.etRepoName.editableText.toString().trim()

        if (repoOwnerName.isEmpty()) {
            binding.etRepoOwnerName.error = resources.getString(R.string.repo_owner_name_required)
            binding.etRepoOwnerName.requestFocus()
        } else if (repoName.isEmpty()) {
            binding.etRepoName.error = resources.getString(R.string.repo_name_required)
            binding.etRepoName.requestFocus()
        } else {
            favGithubRepoViewModel.getFavGithubRepoList(repoOwnerName, repoName)
        }
        bindObservers()
    }

    private fun bindObservers() {
        favGithubRepoViewModel.favGithubRepoResponseLiveData.observe(this, Observer {
            binding.progressBar.visibility = View.GONE       // Hiding progress bar when we observe the data
            when (it) {
                is NetworkResult.Success -> {
                    Toast.makeText(this, resources.getString(R.string.repo_added_successfully), Toast.LENGTH_LONG).show()
                    it.data?.let { data ->
                        favGithubRepoViewModel.insert(this@AddRepoActivity, data)
                    }
                    val intent = Intent(this@AddRepoActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                else -> {

                }
            }
        })
    }
}