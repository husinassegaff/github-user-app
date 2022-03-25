package com.example.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.api.ApiConfig
import com.example.githubuserapp.response.FollowerResponse
import com.example.githubuserapp.response.FollowerResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {

    private val _listFollowers = MutableLiveData<ArrayList<FollowerResponseItem>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowerViewModel"
    }

    fun setFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object: Callback<FollowerResponse> {
            override fun onResponse(
                call: Call<FollowerResponse>,
                response: Response<FollowerResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listFollowers.value = response.body()?.followerResponse
                }
                else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FollowerResponse>, t: Throwable) {
                _isLoading.value = false

                Log.e(TAG, "onFailure: {${t.message.toString()}")
            }
        })
    }

    fun getFollower(): LiveData<ArrayList<FollowerResponseItem>> = _listFollowers
}