package com.example.githubuserapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String,
    var username: String,
    var description: String,
    var location: String,
    var avatar: Int,
    var index: Int

) : Parcelable