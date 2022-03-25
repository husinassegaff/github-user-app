package com.example.githubuserapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListUserAdapter
import com.example.githubuserapp.databinding.FragmentHomeBinding
import com.example.githubuserapp.response.ItemsItem
import com.example.githubuserapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var rvUser: RecyclerView
    private lateinit var searchUser: SearchView
    private lateinit var binding: FragmentHomeBinding
    private lateinit var listUser: ArrayList<ItemsItem>
    private var homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewLifecycleOwner
            HomeViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { searchUsername() }

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
        rvUser.setHasFixedSize(true)

        listUserAdapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {

            override fun onItemClicked(data: ItemsItem) {
                setSelectedUser(data)
            }
        })
    }

    private fun searchUsername() {
        searchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    newText.let { homeViewModel.setUsername(it)}
                    showLoading(true)
                } else {
                    showLoading(false)
                    listUser.clear()
                }
                return true
            }
        })
    }

    private fun setSelectedUser(data: ItemsItem) {
        val mBundle = Bundle()
        mBundle.putParcelable(EXTRA_USER, data)
        NavHostFragment
            .findNavController(this)
            .navigate(R.id.action_homeFragment_to_detailUserFragment, mBundle)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progBar.visibility = View.VISIBLE
        } else {
            binding.progBar.visibility = View.GONE
        }
    }


}