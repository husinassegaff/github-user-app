package com.example.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.api.ApiConfig
import com.example.githubuserapp.response.ItemsItem
import com.example.githubuserapp.response.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _listUsername = MutableLiveData<ArrayList<ItemsItem>>()
    val user: LiveData<ArrayList<ItemsItem>> = _listUsername

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "HomeViewModel"
    }

    fun setUsername(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUser(username)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listUsername.value = response.body()?.items
                }

                else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUsername() : LiveData<ArrayList<ItemsItem>> = _listUsername

}