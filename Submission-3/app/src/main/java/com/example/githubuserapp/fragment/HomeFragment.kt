package com.example.githubuserapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListUserAdapter
import com.example.githubuserapp.databinding.FragmentHomeBinding
import com.example.githubuserapp.databinding.FragmentSettingBinding
import com.example.githubuserapp.datastore.SettingPreferences
import com.example.githubuserapp.response.ItemsItem
import com.example.githubuserapp.viewmodel.HomeViewModel
import com.example.githubuserapp.viewmodel.SettingViewModel
import com.example.githubuserapp.viewmodel.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {

    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var rvUser: RecyclerView
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bindingSetting: FragmentSettingBinding
    private lateinit var switchTheme: SwitchMaterial
    private val listUser =  ArrayList<ItemsItem>()
    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var searchUser : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_favoriteFragment)
                true
            }
            R.id.menu_dark -> {
                NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_settingFragment)
                true
            }
            else -> true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )

        bindingSetting = FragmentSettingBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        switchTheme = bindingSetting.switchTheme
        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        mainViewModel.getThemeSettings().observe(viewLifecycleOwner) {
                isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        rvUser = binding.rvUser
        rvUser.setHasFixedSize(true)
        searchUser = binding.searchUser
        searchUser.queryHint = resources.getString(R.string.search_hint)

        showRecyclerView(listUser)

        searchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    newText.let { homeViewModel.setUsername(it)}
                    showLoading(true)
                } else {
                    showLoading(false)
                }
                return true
            }
        })

        homeViewModel.user.observe(viewLifecycleOwner) {
            showRecyclerView(it)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.getUsername().observe(viewLifecycleOwner) { usernameItems ->
            if (usernameItems != null) showLoading(false)
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    private fun showRecyclerView(listUser: ArrayList<ItemsItem>) {
        rvUser.layoutManager = LinearLayoutManager(activity)
        listUserAdapter = ListUserAdapter(listUser)

        rvUser.adapter = listUserAdapter
        rvUser.itemAnimator = DefaultItemAnimator()
        listUserAdapter.notifyDataSetChanged()

        listUserAdapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {

            override fun onItemClicked(data: ItemsItem) {
                setSelectedUser(data)
            }
        })
    }

    private fun setSelectedUser(data: ItemsItem) {
        val toDetailUserFragment = HomeFragmentDirections.actionHomeFragmentToDetailUserFragment()
        toDetailUserFragment.username = data.login
        NavHostFragment.findNavController(this).navigate(toDetailUserFragment)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progBar.visibility = View.VISIBLE
        } else {
            binding.progBar.visibility = View.GONE
        }
    }


}