package com.spidy.instagramclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.spidy.instagramclone.R
import com.spidy.instagramclone.adapters.MyPostAdapter
import com.spidy.instagramclone.databinding.FragmentMyPostBinding
import com.spidy.instagramclone.models.Post

class MyPostFragment : Fragment() {
    private lateinit var binding: FragmentMyPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPostBinding.inflate(inflater, container, false)

        var postList = ArrayList<Post>()
        var adapter = MyPostAdapter(requireContext(), postList)
        binding.rvPost.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        binding.rvPost.adapter = adapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            var tempList = arrayListOf<Post>()
            for (i in it.documents) {
                var post: Post = i.toObject<Post>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object
}