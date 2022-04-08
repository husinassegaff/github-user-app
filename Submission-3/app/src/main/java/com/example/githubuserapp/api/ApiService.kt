package com.example.githubuserapp.api

import com.example.githubuserapp.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/users/{login}")
    fun getUser(
        @Path("login") login : String
    ): Call<GithubAPIResponse>

    @GET("/users/{login}/following")
    fun getUserFollowing(
        @Path("login") login : String
    ): Call<ArrayList<FollowingResponseItem>>

    @GET("/users/{login}/followers")
    fun getUserFollowers(
        @Path("login") login: String
    ): Call<ArrayList<FollowerResponseItem>>

    @GET("/search/users")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<SearchResponse>
}