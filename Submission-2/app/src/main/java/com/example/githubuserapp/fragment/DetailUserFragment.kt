package com.example.githubuserapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.api.User
import com.example.githubuserapp.databinding.FragmentDetailUserBinding
import com.example.githubuserapp.response.GithubAPIResponse
import com.example.githubuserapp.viewmodel.DetailUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserFragment : Fragment() {

    private lateinit var usernameUser: String
    private lateinit var binding: FragmentDetailUserBinding
    private var detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_following,
            R.string.tab_followers
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailUserBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewLifecycleOwner
            DetailUserViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSelectedUser()
        showLoadingUserDetail(true)
        setViewPager()

        usernameUser.let { detailUserViewModel.setDetailUser(it)}
        detailUserViewModel.getUser().observe (viewLifecycleOwner) { detailUserItems ->
            if (detailUserItems != null) {
                showUserDetails(detailUserItems)
                showLoadingUserDetail(false)
            }
        }

        detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoadingUserDetail(it)
        }
    }

    private fun getSelectedUser() {
        val detailUser = arguments?.getParcelable<User>(HomeFragment.EXTRA_USER) as User
        usernameUser = detailUser.login
    }

    private fun showUserDetails(detailUserItems: GithubAPIResponse) {
        binding.dataName.text = detailUserItems.name
        binding.dataUsername.text = detailUserItems.login

        val followers = "Followers : ${detailUserItems.followers}"
        binding.dataFollowers.text = followers

        val following = "Following : ${detailUserItems.following}"
        binding.dataFollowing.text = following

        val repository = "Public Repo : ${detailUserItems.publicRepos}"
        binding.dataRepository.text = repository

        if (detailUserItems.location.isEmpty() || detailUserItems.location == "null") {
            val location = "No Location"
            binding.dataLocation.text = location
        } else {
            binding.dataLocation.text = detailUserItems.location
        }
        if (detailUserItems.company.isEmpty() || detailUserItems.company == "null") {
            val company = "No Company"
            binding.dataCompany.text = company
        } else {
            binding.dataCompany.text = detailUserItems.company
        }
    }

    private fun showLoadingUserDetail(isLoading: Boolean) {
        if (isLoading) {
            binding.groupLoadingBarUser.visibility = View.VISIBLE
        }
        else {
            binding.groupLoadingBarUser.visibility = View.GONE
        }
    }

    private fun setViewPager() {
        val sectionsPagerAdapter = context?.let {
            SectionsPagerAdapter(it as AppCompatActivity)
        }

        sectionsPagerAdapter?.setUsernameUser(usernameUser)
        val viewPager: ViewPager2 = binding.viewPagerFollowingFollowers
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabsFollowingFollowers
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

       activity?.actionBar?.elevation = 0f
    }

}