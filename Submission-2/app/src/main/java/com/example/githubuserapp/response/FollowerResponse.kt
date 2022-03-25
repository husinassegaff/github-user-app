package com.example.githubuserapp.response

import com.google.gson.annotations.SerializedName

data class FollowerResponse(

	@field:SerializedName("FollowerResponse")
	val followerResponse: ArrayList<FollowerResponseItem>
)

data class FollowerResponseItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int,

)
