package com.spidy.instagramclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.spidy.instagramclone.R
import com.spidy.instagramclone.Utils.POST
import com.spidy.instagramclone.adapters.PostAdapter
import com.spidy.instagramclone.databinding.FragmentHomeBinding
import com.spidy.instagramclone.models.Post

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var postList = ArrayList<Post>()
    private lateinit var adapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)


        adapter = PostAdapter(requireContext(), postList)
        binding.rvPost.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPost.adapter = adapter

        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var tempList = ArrayList<Post>()
            postList.clear()
            for (i in it.documents) {
                var post: Post = i.toObject<Post>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
    }
}