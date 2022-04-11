package com.example.githubuserapp.unittest

import com.example.githubuserapp.viewmodel.FavoriteViewModel
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class FavoriteViewModelTest {

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var list: ArrayList<String>
    private val username = "username"

    @Before
    fun before() {
        viewModel = FavoriteViewModel()
        list = ArrayList()
    }


    @Test
    fun getAllFavorites() {
        list.add(username)
        viewModel.set
        assertEquals(list, viewModel.getFavorites())
    }
}