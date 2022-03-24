package com.example.githubuserapp.response

import com.google.gson.annotations.SerializedName

data class GithubAPIResponse(

	@field:SerializedName("bio")
	val bio: Any,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("company")
	val company: Any,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("public_repos")
	val publicRepos: Int
)
