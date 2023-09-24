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
import com.spidy.instagramclone.R
import com.spidy.instagramclone.Utils.REEL
import com.spidy.instagramclone.adapters.MyPostAdapter
import com.spidy.instagramclone.adapters.MyReelAdapter
import com.spidy.instagramclone.databinding.FragmentReelBinding
import com.spidy.instagramclone.models.Post
import com.spidy.instagramclone.models.Reel

class MyReelsFragment : Fragment() {
    private lateinit var binding: FragmentReelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReelBinding.inflate(inflater, container, false)

        var reelList = ArrayList<Reel>()
        var adapter = MyReelAdapter(requireContext(), reelList)
        binding.rvReels.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        binding.rvReels.adapter = adapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).get()
            .addOnSuccessListener {
                var tempList = arrayListOf<Reel>()
                for (i in it.documents) {
                    var reel: Reel = i.toObject<Reel>()!!
                    tempList.add(reel)
                }
                reelList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }

        return binding.root
    }

    companion object {
    }
}