package com.spidy.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.spidy.instagramclone.R
import com.spidy.instagramclone.Utils.FOLLOW
import com.spidy.instagramclone.databinding.RowSearchBinding
import com.spidy.instagramclone.models.User

class SearchAdapter(var context: Context, var userList: ArrayList<User>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: RowSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowSearchBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var isFollow = false
        Glide.with(context).load(userList[position].image)
            .placeholder(R.drawable.ic_profile_placeholder).into(holder.binding.ivProfile)

        holder.binding.userName.text = userList[position].name

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).whereEqualTo("email",userList[position].email).get().addOnSuccessListener {
            if(it.documents.size != 0){
                holder.binding.follow.text = "Unfollow"
                isFollow = true
            }
        }

        holder.binding.follow.setOnClickListener {
            if(isFollow){
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).whereEqualTo("email",userList[position].email).get().addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).document(
                        it.documents[0].id).delete()
                    holder.binding.follow.text = "Follow"
                    isFollow = false
                }
            }else {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).document()
                    .set(userList[position])
                holder.binding.follow.text = "Unfollow"
                isFollow = true;
            }
        }
    }

}