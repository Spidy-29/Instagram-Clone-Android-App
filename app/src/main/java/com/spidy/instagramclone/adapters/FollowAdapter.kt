package com.spidy.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.play.integrity.internal.c
import com.spidy.instagramclone.R
import com.spidy.instagramclone.databinding.RowFollowBinding
import com.spidy.instagramclone.models.User

class FollowAdapter(var context: Context, var followList: ArrayList<User>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: RowFollowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowFollowBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return followList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(followList[position].image).placeholder(R.drawable.ic_profile_placeholder).into(holder.binding.ivProfile)
        holder.binding.name.text = followList[position].name
    }

}