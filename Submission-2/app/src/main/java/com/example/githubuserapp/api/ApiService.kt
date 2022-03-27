package com.example.githubuserapp.api

import com.example.githubuserapp.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/users/{login}")
    @Headers("Authorization: token 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")
    fun getUser(
        @Path("login") login : String
    ): Call<GithubAPIResponse>

    @GET("/users/{login}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("login") login : String
    ): Call<ArrayList<FollowerResponseItem>>

    @GET("/users/{login}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("login") login: String
    ): Call<ArrayList<FollowingResponseItem>>

    @GET("/search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<SearchResponse>
}