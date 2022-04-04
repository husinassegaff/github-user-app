package com.example.githubuserapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.adapter.FavoriteAdapter
import com.example.githubuserapp.databinding.FragmentFavoriteBinding
import com.example.githubuserapp.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private lateinit var listFavoriteAdapter: FavoriteAdapter
    private lateinit var rvFavorite: RecyclerView
    private lateinit var binding: FragmentFavoriteBinding


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

        rvFavorite = binding.rvFavorite
        rvFavorite.layoutManager = LinearLayoutManager(activity)
        rvFavorite.setHasFixedSize(true)

        rvFavorite.adapter = listFavoriteAdapter
    }

}