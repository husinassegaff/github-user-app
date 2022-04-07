package com.example.githubuserapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.database.Favorite
import com.example.githubuserapp.repository.FavoriteRepository

class FavoriteAddUpdateViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun update(favorite: Favorite) {
        mFavoriteRepository.update(favorite)
    }

    fun delete(username: String) {
        mFavoriteRepository.delete(username)
    }

    fun exist(username: String) : Boolean {
        return mFavoriteRepository.exist(username)
    }
}