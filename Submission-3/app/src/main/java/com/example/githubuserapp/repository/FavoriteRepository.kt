package com.example.githubuserapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapp.database.Favorite
import com.example.githubuserapp.database.FavoriteDao
import com.example.githubuserapp.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {

    private val mFavoritesDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoritesDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoritesDao.getAllFavorites()

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoritesDao.insert(favorite) }
    }

    fun delete(username: String) {
        executorService.execute { mFavoritesDao.delete(username) }
    }

    fun exist(username: String) : Boolean {
        return mFavoritesDao.exist(username)
    }
}