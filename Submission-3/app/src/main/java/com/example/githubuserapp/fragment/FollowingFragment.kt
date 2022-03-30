package com.example.githubuserapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.adapter.FollowingAdapter
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.databinding.FragmentFollowingBinding
import com.example.githubuserapp.response.FollowingResponseItem
import com.example.githubuserapp.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {

    private lateinit var listFollowingAdapter: FollowingAdapter
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var rvFollowing : RecyclerView
    private lateinit var usernameUser :String

    private var listFollowing = ArrayList<FollowingResponseItem>()
    private val followingViewModel by viewModels<FollowingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewLifecycleOwner
            FollowingViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFollowing = binding.rvFollowings
        rvFollowing.setHasFixedSize(true)

        showLoadingFollowing(true)
        getUsernameUser()
        showRecyclerView(listFollowing)

        usernameUser.let { followingViewModel.setFollowing(it) }

        followingViewModel.getFollowing().observe(viewLifecycleOwner) {  listFollowingItems ->
            if (listFollowingItems.size > 0) {
                showFollowingItems(listFollowingItems)
                showRecyclerView(listFollowingItems)
                showLoadingFollowing(false)
            }
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoadingFollowing(it)
        }
    }

    private fun getUsernameUser() {
        if(arguments != null){
            usernameUser = arguments?.getString(SectionsPagerAdapter.EXTRA_USERNAME).toString()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showRecyclerView(dataFollowing : ArrayList<FollowingResponseItem>) {
        rvFollowing.layoutManager = LinearLayoutManager(activity)
        listFollowingAdapter = FollowingAdapter(dataFollowing)

        rvFollowing.adapter = listFollowingAdapter
        rvFollowing.itemAnimator = DefaultItemAnimator()
        listFollowingAdapter.notifyDataSetChanged()
    }

    private fun showFollowingItems(listFollowingItems: ArrayList<FollowingResponseItem>) {
        listFollowingAdapter.setFollowingData(listFollowingItems)

        when (listFollowingItems.size) {
            0 -> binding.tvNoFollowings.visibility = View.VISIBLE

            else -> binding.tvNoFollowings.visibility = View.GONE
        }
    }

    private fun showLoadingFollowing(isLoading: Boolean) {
        if (isLoading) {
            binding.progBarFollowing.visibility = View.VISIBLE
        }
        else {
            binding.progBarFollowing.visibility = View.GONE
        }
    }

}