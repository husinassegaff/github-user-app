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
import com.example.githubuserapp.response.ItemsItem
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
    private lateinit var detailUser: ItemsItem
    private var checkExist: Boolean? = null

    private var optionMenu : Menu? = null

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        optionMenu = menu
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menu_favorite).isVisible = false
        menu.findItem(R.id.menu_dark).isVisible = false
    }

    private fun optionMenuDelete(menu: Menu, checkExist: Boolean) {
        menu.findItem(R.id.menu_delete).isVisible = checkExist
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
        detailUser = arguments?.getParcelable<ItemsItem>(HomeFragment.EXTRA_USER) as ItemsItem
        usernameUser = detailUser.login

        showLoadingUserDetail(true)

        favoriteAddUpdateViewModel = FavoriteAddUpdateViewModel(activity?.applicationContext as Application)
        favorite = Favorite()


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

        checkExist = isExist(detailUser.login)

        if (checkExist == true) {
            optionMenuDelete(optionMenu!!, checkExist!!)
        }


        binding.fabFavorite.setOnClickListener(this)
    }

    private fun isExist(username: String) : Boolean {
        return favoriteAddUpdateViewModel.exist(username)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.fab_favorite -> {
                if (checkExist == false) {
                    favorite?.username = detailUser.login
                    favorite?.avatar_url = detailUser.avatarUrl
                    favorite?.id_user = detailUser.id
                    favorite?.user_type = detailUser.type
                    favoriteAddUpdateViewModel.insert(favorite as Favorite)

                    showToast(getString(R.string.added))
                    checkExist = true
                }
            }
            optionMenu?.findItem(R.id.menu_delete)?.itemId -> {
                favoriteAddUpdateViewModel.delete(detailUser.login)
                showToast(getString(R.string.deleted))
                checkExist = false
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