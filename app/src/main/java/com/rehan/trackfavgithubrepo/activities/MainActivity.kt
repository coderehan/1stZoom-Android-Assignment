package com.rehan.trackfavgithubrepo.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rehan.trackfavgithubrepo.R
import com.rehan.trackfavgithubrepo.adapters.TrackFavGithubRepoAdapter
import com.rehan.trackfavgithubrepo.databinding.ActivityMainBinding
import com.rehan.trackfavgithubrepo.models.TrackFavGithubRepoDTOResponse
import com.rehan.trackfavgithubrepo.models.TrackFavGithubRepoResponse
import com.rehan.trackfavgithubrepo.viewmodels.TrackFavGithubRepoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var favGithubRepoAdapter: TrackFavGithubRepoAdapter
    private lateinit var favGithubRepoViewModel: TrackFavGithubRepoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        favGithubRepoViewModel = ViewModelProvider(this)[TrackFavGithubRepoViewModel::class.java]

        setupRecyclerView()
        bindObservers()
        onFavGithubRepoClick()
    }

    // This function will take the user to particular github repo link in browser
    private fun onFavGithubRepoClick() {
        favGithubRepoAdapter.onItemClick = {
            Log.d("TAG", "onFavGithubRepoClick: " + it)
            val result = Intent(Intent.ACTION_VIEW,  Uri.parse(it))
            startActivity(Intent(result))
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.add_menu) {
            val intent = Intent(this@MainActivity, AddRepoActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    private fun setupRecyclerView() {
        favGithubRepoAdapter = TrackFavGithubRepoAdapter(this@MainActivity)
        binding.rvFavGithubRepo.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = favGithubRepoAdapter
        }
    }

    private fun bindObservers() {
        favGithubRepoViewModel.getAllFavGithubRepoData(applicationContext)?.observe(this, Observer {
            favGithubRepoAdapter.setData(it as ArrayList<TrackFavGithubRepoResponse>)
        })
    }

}