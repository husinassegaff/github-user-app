package com.example.githubuserapp.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val login: String,
    val type: String,
    val avatarUrl: String,
    val id: Int,
): Parcelable

//@Parcelize
//data class UserDetail(
//    val name: String,
//    val login: String,
//    val location: String,
//    val avatarUrl: String,
//    val followers: Int,
//    val following: Int,
//    val publicRepos: Int,
//    val company: String,
//) : Parcelable


//@Parcelize
//data class Followers(
//    val login: String,
//    val type: String,
//    val avatarUrl: String,
//    val id: Int,
//) : Parcelable
//
//
//@Parcelize
//data class Following(
//    val login: String,
//    val type: String,
//    val avatarUrl: String,
//    val id: Int,
//) : Parcelable