package com.spidy.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spidy.instagramclone.databinding.RowMyPostBinding
import com.spidy.instagramclone.models.Post
import com.squareup.picasso.Picasso

class MyPostAdapter(var context: Context, var postList: ArrayList<Post>) :
    RecyclerView.Adapter<MyPostAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: RowMyPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = RowMyPostBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(postList[position].postUrl).into(holder.binding.ivPostImage)
    }
}