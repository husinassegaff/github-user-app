package com.example.githubuserapp.response

import com.google.gson.annotations.SerializedName

data class FollowingResponse(

	@field:SerializedName("FollowingResponse")
	val followingResponse: ArrayList<FollowingResponseItem>
)

data class FollowingResponseItem(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

)
