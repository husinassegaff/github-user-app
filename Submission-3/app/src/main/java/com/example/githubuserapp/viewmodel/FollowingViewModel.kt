package com.example.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.api.ApiConfig
import com.example.githubuserapp.response.FollowingResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    private val _listFollowing = MutableLiveData<ArrayList<FollowingResponseItem>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowingViewModel"
    }

    fun setFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<ArrayList<FollowingResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<FollowingResponseItem>>,
                response: Response<ArrayList<FollowingResponseItem>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                }

                else {
                    Log.e(TAG, "onFailure: ${response.message()} and ${response.body()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<FollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(): LiveData<ArrayList<FollowingResponseItem>> = _listFollowing
}