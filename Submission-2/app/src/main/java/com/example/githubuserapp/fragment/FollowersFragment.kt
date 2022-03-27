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
import com.example.githubuserapp.adapter.FollowerAdapter
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.databinding.FragmentFollowersBinding
import com.example.githubuserapp.response.FollowerResponseItem
import com.example.githubuserapp.viewmodel.FollowerViewModel

class FollowersFragment : Fragment() {

    private lateinit var listFollowerAdapter: FollowerAdapter
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var rvFollower : RecyclerView
    private lateinit var usernameUser : String

    private var listFollowers = ArrayList<FollowerResponseItem>()
    private val followerViewModel by viewModels<FollowerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewLifecycleOwner
            FollowerViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFollower = binding.rvFollowers

        showLoadingFollowers(true)
        getUsernameUser()
        showRecyclerView()

        usernameUser.let {followerViewModel.setFollower(it)}

        followerViewModel.getFollower().observe(viewLifecycleOwner) { listFollowerItems ->
            if (listFollowerItems.size > 0) {
                showFollowerItems(listFollowerItems)
                showLoadingFollowers(false)
            }
        }

        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoadingFollowers(it)
        }
    }

    private fun getUsernameUser() {
        if (arguments != null) {
            usernameUser = arguments?.getString(SectionsPagerAdapter.EXTRA_USERNAME).toString()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showRecyclerView() {
        rvFollower.layoutManager = LinearLayoutManager(activity)
        listFollowerAdapter = FollowerAdapter(listFollowers)

        rvFollower.adapter = listFollowerAdapter
        rvFollower.itemAnimator = DefaultItemAnimator()
        listFollowerAdapter.notifyDataSetChanged()
        rvFollower.setHasFixedSize(true)
    }

    private fun showFollowerItems(listFollowerItems: ArrayList<FollowerResponseItem>) {
        listFollowerAdapter.setFollowerData(listFollowerItems)

        when (listFollowerItems.size) {
            0 -> binding.tvNoFollowers.visibility = View.VISIBLE
            else -> binding.tvNoFollowers.visibility = View.GONE
        }
    }

    private fun showLoadingFollowers(isLoading: Boolean) {
        if (isLoading) {
            binding.progBarFollower.visibility = View.VISIBLE
        }
        else {
            binding.progBarFollower.visibility = View.GONE
        }
    }


}