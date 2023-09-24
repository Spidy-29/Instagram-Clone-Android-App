package com.spidy.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.spidy.instagramclone.databinding.RowMyPostBinding
import com.spidy.instagramclone.models.Reel

class MyReelAdapter(var context: Context, var reelList: ArrayList<Reel>) :
    RecyclerView.Adapter<MyReelAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: RowMyPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = RowMyPostBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(reelList[position].reelUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.ivPostImage)
    }
}