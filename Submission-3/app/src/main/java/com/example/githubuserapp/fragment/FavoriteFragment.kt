package com.example.githubuserapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.adapter.FavoriteAdapter
import com.example.githubuserapp.database.Favorite
import com.example.githubuserapp.databinding.FragmentFavoriteBinding
import com.example.githubuserapp.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private lateinit var listFavoriteAdapter: FavoriteAdapter
    private val listFavorite =  ArrayList<Favorite>()
    private lateinit var rvFavorite: RecyclerView
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteViewModel : FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewLifecycleOwner
            FavoriteViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel = FavoriteViewModel(requireActivity().application)

        rvFavorite = binding.rvFavorite
        rvFavorite.setHasFixedSize(true)

        showRecyclerView(listFavorite)

        favoriteViewModel.getAllFavorites().observe(viewLifecycleOwner) {
            showRecyclerView(it as ArrayList<Favorite>)
        }
    }

    private fun showRecyclerView(listFavorite: ArrayList<Favorite>) {
        rvFavorite.layoutManager = LinearLayoutManager(activity)
        listFavoriteAdapter = FavoriteAdapter()

        listFavoriteAdapter.setListFavorites(listFavorite)
        rvFavorite.adapter = listFavoriteAdapter

        rvFavorite.itemAnimator = DefaultItemAnimator()

        listFavoriteAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Favorite) {
                setSelectedUser(data)
            }
        })
    }

    private fun setSelectedUser(data: Favorite) {
        val toDetailUserFragment = FavoriteFragmentDirections.actionFavoriteFragmentToDetailUserFragment()
        toDetailUserFragment.username = data.username.toString()
        NavHostFragment.findNavController(this).navigate(toDetailUserFragment)

    }
}