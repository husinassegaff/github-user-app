package com.example.githubuserapp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorite (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "username")
    var username: String? = null,


    @ColumnInfo(name = "followers")
    var followers: String? = null,

    @ColumnInfo(name = "repository")
    var repository: String? = null,

    @ColumnInfo(name = "avatar_url")
    var avatar_url: String? = null
) : Parcelable