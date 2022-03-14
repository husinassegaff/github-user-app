package com.example.githubuserapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailUser : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val data = intent.getParcelableExtra<User>("DATA")

        val tvAvatar: ImageView = findViewById(R.id.data_avatar)
        val tvName: TextView = findViewById(R.id.data_name)
        val tvUsername: TextView = findViewById(R.id.data_username)
        val tvFollowers: TextView = findViewById(R.id.data_followers)
        val tvFollowing: TextView = findViewById(R.id.data_following)
        val tvRepository: TextView = findViewById(R.id.data_repository)
        val tvCompany: TextView = findViewById(R.id.data_company)
        val tvLocation: TextView = findViewById(R.id.data_location)

        if (data != null) {
            tvAvatar.setImageResource(data.avatar)
            tvName.text = data.name
            tvUsername.text = data.username
            tvLocation.text = data.location
            tvFollowers.text = "${data.followers} Followers"
            tvFollowing.text = "${data.following} Following"
            tvRepository.text = "${data.repository} Repository"
            tvCompany.text = data.company
        }
    }
}