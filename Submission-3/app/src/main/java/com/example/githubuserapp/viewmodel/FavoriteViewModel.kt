package com.example.githubuserapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.database.Favorite
import com.example.githubuserapp.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    companion object {
        private const val TAG = "FavoriteViewModel"
    }

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllNotes(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()
}