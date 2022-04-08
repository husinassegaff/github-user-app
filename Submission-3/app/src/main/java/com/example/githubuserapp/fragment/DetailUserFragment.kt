package com.example.githubuserapp.fragment

import android.app.Application
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.database.Favorite
import com.example.githubuserapp.databinding.FragmentDetailUserBinding
import com.example.githubuserapp.response.GithubAPIResponse
import com.example.githubuserapp.viewmodel.DetailUserViewModel
import com.example.githubuserapp.viewmodel.FavoriteAddUpdateViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteAddUpdateViewModel : FavoriteAddUpdateViewModel
    private var favorite: Favorite? = null

    private val detailUserViewModel by viewModels<DetailUserViewModel>()

    private lateinit var usernameUser: String
    private lateinit var detailUser: GithubAPIResponse
    private var checkExist: Boolean? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following

        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailUserBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingUserDetail(true)

        favoriteAddUpdateViewModel = FavoriteAddUpdateViewModel(activity?.applicationContext as Application)
        favorite = Favorite()

        usernameUser = DetailUserFragmentArgs.fromBundle(arguments as Bundle).username
        usernameUser.let { detailUserViewModel.setDetailUser(it)}
        setViewPager()

        checkExist = isExist(usernameUser)

        if(checkExist == true) {
            binding.fabFavorite.setImageResource(R.drawable.ic_clear)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_white_24)
        }

        detailUserViewModel.getUser().observe (viewLifecycleOwner) { detailUserItems ->
            if (detailUserItems != null) {
                detailUser = detailUserItems
                showUserDetails(detailUserItems)

                showLoadingUserDetail(false)
            }
        }

        detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoadingUserDetail(it)
        }

        binding.fabFavorite.setOnClickListener(this)
    }

    private fun isExist(username: String) : Boolean {
        return favoriteAddUpdateViewModel.exist(username)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.fab_favorite -> {
                checkExist = isExist(usernameUser)

                if (checkExist == false) {
                    favorite?.username = detailUser.login
                    favorite?.avatar_url = detailUser.avatarUrl
                    favorite?.followers = resources.getString(R.string.followers, detailUser.followers)
                    favorite?.repository = resources.getString(R.string.repositories, detailUser.publicRepos)
                    favoriteAddUpdateViewModel.insert(favorite as Favorite)

                    binding.fabFavorite.setImageResource(R.drawable.ic_clear)
                    showToast(getString(R.string.added))
                } else{
                    favoriteAddUpdateViewModel.delete(usernameUser)
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite_white_24)
                    showToast(getString(R.string.deleted))
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showUserDetails(detailUserItems: GithubAPIResponse) {
        binding.dataName.text = detailUserItems.name
        binding.dataUsername.text = detailUserItems.login

        binding.dataFollowers.text = resources.getString(R.string.followers, detailUserItems.followers)
        binding.dataFollowing.text = resources.getString(R.string.following, detailUserItems.following)
        binding.dataRepository.text = resources.getString(R.string.repositories, detailUserItems.publicRepos)

        if (detailUserItems.location == null) {
            binding.dataLocation.text = resources.getString(R.string.no_location)
        } else {
            binding.dataLocation.text = detailUserItems.location
        }
        if (detailUserItems.company == null ) {
            binding.dataCompany.text = resources.getString(R.string.no_company)
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