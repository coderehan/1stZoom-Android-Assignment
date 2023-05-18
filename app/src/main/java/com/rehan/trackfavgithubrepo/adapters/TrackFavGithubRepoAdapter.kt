package com.rehan.trackfavgithubrepo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rehan.trackfavgithubrepo.databinding.AdapterTrackFavGithubRepoItemsBinding
import com.rehan.trackfavgithubrepo.models.TrackFavGithubRepoResponse
import com.squareup.picasso.Picasso

class TrackFavGithubRepoAdapter(private val context: Context) : RecyclerView.Adapter<TrackFavGithubRepoAdapter.ViewHolder>() {

    private var favGithubRepoList = ArrayList<TrackFavGithubRepoResponse>()
    var onItemClick: ((String) -> Unit)? = null

    // First we will get data from database and then we will set here
    @SuppressLint("NotifyDataSetChanged")
    fun setData(favGithubRepoList: ArrayList<TrackFavGithubRepoResponse>) {
        this.favGithubRepoList = favGithubRepoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterTrackFavGithubRepoItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(favGithubRepoList[position].avatar_url).into(holder.binding.ivUser)
        holder.binding.tvRepoName.text = favGithubRepoList[position].name
        holder.binding.tvRepoDescriptionName.text = "This repository is developed by " + favGithubRepoList[position].login + "." + " " + "This project is written in " + favGithubRepoList[position].language + " programming language."
        holder.binding.btnShare.setOnClickListener {
            val shareData = Intent(Intent.ACTION_SEND)
            shareData.type = "text/plain"
            val dataToShare = "This repository is developed by " + favGithubRepoList[position].login + "." + " " + "This project is written in " + favGithubRepoList[position].language + " programming language." + "\n\n" + "Project Source Code Link :" + favGithubRepoList[position].html_url + "\n\n" + "Happy Coding!!"
            shareData.putExtra(Intent.EXTRA_SUBJECT, "Track Favourite Github Repo Android App")
            shareData.putExtra(Intent.EXTRA_TEXT, dataToShare)
            context.startActivity(Intent.createChooser(shareData, "Share Via"))
        }
        // When user clicks on item views in recyclerview
        holder.binding.root.setOnClickListener {
            favGithubRepoList[position].html_url?.let { urlInfo -> onItemClick!!.invoke(urlInfo) }
        }
    }

    override fun getItemCount(): Int {
        return favGithubRepoList.size
    }


    inner class ViewHolder(val binding: AdapterTrackFavGithubRepoItemsBinding) : RecyclerView.ViewHolder(binding.root)

}