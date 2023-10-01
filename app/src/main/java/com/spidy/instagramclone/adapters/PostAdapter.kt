package com.spidy.instagramclone.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.spidy.instagramclone.R
import com.spidy.instagramclone.Utils.USER_NODE
import com.spidy.instagramclone.databinding.RowPostBinding
import com.spidy.instagramclone.models.Post
import com.spidy.instagramclone.models.User

class PostAdapter(var context: Context, var postList: ArrayList<Post>) :
    RecyclerView.Adapter<PostAdapter.MyHolder>() {
    inner class MyHolder(var binding: RowPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var binding = RowPostBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        try {
            Firebase.firestore.collection(USER_NODE).document(postList[position].uid).get()
                .addOnSuccessListener {
                    var user = it.toObject<User>()
                    Glide.with(context).load(user!!.image)
                        .placeholder(R.drawable.ic_profile_placeholder)
                        .into(holder.binding.ivProfile)
                    holder.binding.name.text = user.name
                }
            val text: String = TimeAgo.using(postList[position].time.toLong())
            holder.binding.time.text = text
        } catch (e: Exception) {
        }

        Glide.with(context).load(postList[position].postUrl)
            .placeholder(R.drawable.loading_image).into(holder.binding.postImage)

        holder.binding.ivShare.setOnClickListener {
            var i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_TEXT, postList[position].postUrl)
            context.startActivity(i)
        }
        holder.binding.caption.text = postList[position].caption
        var like: Boolean = false
        holder.binding.ivLike.setOnClickListener {
            if (like) {
                holder.binding.ivLike.setImageResource(R.drawable.like_pic)
                like = false
            } else {
                holder.binding.ivLike.setImageResource(R.drawable.liked_pic)
                like = true
            }
        }
    }
}