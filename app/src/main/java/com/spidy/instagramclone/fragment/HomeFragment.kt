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
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.spidy.instagramclone.R
import com.spidy.instagramclone.Utils.FOLLOW
import com.spidy.instagramclone.Utils.POST
import com.spidy.instagramclone.Utils.USER_NODE
import com.spidy.instagramclone.adapters.FollowAdapter
import com.spidy.instagramclone.adapters.PostAdapter
import com.spidy.instagramclone.databinding.FragmentHomeBinding
import com.spidy.instagramclone.models.Post
import com.spidy.instagramclone.models.User
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var postList = ArrayList<Post>()
    private var followList = ArrayList<User>()
    private lateinit var adapter: PostAdapter
    private lateinit var followAdapter: FollowAdapter
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
        binding.rvFollowers.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        followAdapter = FollowAdapter(requireContext(),followList)
        binding.rvPost.adapter = adapter
        binding.rvFollowers.adapter = followAdapter

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

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).get().addOnSuccessListener {
            var tempList = ArrayList<User>()
            followList.clear()
            for (i in it.documents) {
                var user: User = i.toObject<User>()!!
                tempList.add(user)
            }
            followList.addAll(tempList)
            followAdapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener {
                val user: User = it.toObject<User>()!!
                if (!user.image.isNullOrEmpty())
                    Picasso.get().load(user.image).into(binding.userProfile)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
    }
}