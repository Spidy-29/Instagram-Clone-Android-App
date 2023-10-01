package com.spidy.instagramclone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.spidy.instagramclone.R
import com.spidy.instagramclone.Utils.USER_NODE
import com.spidy.instagramclone.adapters.SearchAdapter
import com.spidy.instagramclone.databinding.FragmentSearchBinding
import com.spidy.instagramclone.models.User

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    var userList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentSearchBinding.inflate(inflater, container, false)


        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter(requireContext(), userList)
        binding.rvSearch.adapter = adapter

        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var tempList = ArrayList<User>()
            userList.clear()
            for (i in it.documents) {
                if (i.id != Firebase.auth.currentUser!!.uid) {
                    var user = i.toObject<User>()!!
                    tempList.add(user)
                }
            }
            userList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        binding.btnSearch.setOnClickListener {
            var text = binding.searchView.text.toString()
            Firebase.firestore.collection(USER_NODE).whereArrayContains("name", text).get()
                .addOnSuccessListener {
                    var tempList = ArrayList<User>()
                    userList.clear()
                    if (!it.isEmpty) {
                        for (i in it.documents) {
                            if (i.id != Firebase.auth.currentUser!!.uid) {
                                var user = i.toObject<User>()!!
                                tempList.add(user)
                            }
                        }
                    }
                    userList.addAll(tempList)
                    adapter.notifyDataSetChanged()
                }
        }

        return binding.root
    }

    companion object {
    }
}