package com.example.githubuserapp.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.databinding.FragmentDetailUserBinding
import com.example.githubuserapp.response.GithubAPIResponse
import com.example.githubuserapp.response.ItemsItem
import com.example.githubuserapp.viewmodel.DetailUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserFragment : Fragment() {

    private lateinit var usernameUser: String
    private lateinit var binding: FragmentDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel>()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following

        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menu_favorite).isVisible = false
        menu.findItem(R.id.menu_dark).isVisible = false
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

        usernameUser.let { detailUserViewModel.setDetailUser(it)}
        setViewPager()

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
        val detailUser = arguments?.getParcelable<ItemsItem>(HomeFragment.EXTRA_USER) as ItemsItem
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

        if (detailUserItems.location == null) {
            val location = "No Location"
            binding.dataLocation.text = location
        } else {
            binding.dataLocation.text = detailUserItems.location
        }
        if (detailUserItems.company == null ) {
            val company = "No Company"
            binding.dataCompany.text = company
        } else {
            binding.dataCompany.text = detailUserItems.company
        }

        Glide.with(this)
            .load(detailUserItems.avatarUrl)
            .circleCrop()
            .into(binding.dataAvatar)
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