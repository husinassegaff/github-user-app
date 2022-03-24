package com.example.githubuserapp.api

import com.example.githubuserapp.response.FollowerResponse
import com.example.githubuserapp.response.FollowingResponse
import com.example.githubuserapp.response.GithubAPIResponse
import com.example.githubuserapp.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/users/{username}")
    fun getUser(
        @Path("login") login : String
    ): Call<GithubAPIResponse>

    @GET("/users/{username}/following")
    fun getUserFollowing(
        @Path("login") login : String
    ): Call<FollowerResponse>

    @GET("/users/{username/followers")
    fun getUserFollowers(
        @Path("login") login: String
    ): Call<FollowingResponse>

    @GET("/search/users?q={husinassegaff}")
    fun getSearchUser(
        @Path("login") login: String
    ): Call<SearchResponse>
}