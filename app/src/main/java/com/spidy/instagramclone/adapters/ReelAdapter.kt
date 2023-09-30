package com.spidy.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spidy.instagramclone.R
import com.spidy.instagramclone.databinding.ReelDgBinding
import com.spidy.instagramclone.models.Reel
import com.squareup.picasso.Picasso

class ReelAdapter(val context: Context, val reelList: List<Reel>) :
    RecyclerView.Adapter<ReelAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ReelDgBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReelDgBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(reelList[position].profileLink)
            .placeholder(R.drawable.ic_profile_placeholder).into(holder.binding.ivProfile)
        holder.binding.tvContent.text = reelList[position].caption
        holder.binding.videoView.setVideoPath(reelList[position].reelUrl)
        holder.binding.videoView.setOnPreparedListener {
            holder.binding.progressBar.visibility = View.GONE
            holder.binding.videoView.start()
        }
    }
}