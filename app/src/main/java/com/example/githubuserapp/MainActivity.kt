package com.example.githubuserapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvUsers: RecyclerView
    private val list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUsers = findViewById(R.id.rv_user)
        rvUsers.setHasFixedSize(true)

        list.addAll(listUsers)
        showRecyclerList()
    }

    private val listUsers: ArrayList<User>
        @SuppressLint("Recycle")
        get() {
            val dataName = resources.getStringArray(R.array.name)
            val dataUsername = resources.getStringArray(R.array.username)
            val dataDescription = resources.getStringArray(R.array.description)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataPhoto = resources.obtainTypedArray(R.array.avatar)
            val listUser = ArrayList<User>()
            for (i in dataName.indices) {
                val user = User(
                    dataName[i],
                    dataUsername[i],
                    dataDescription[i],
                    dataLocation[i],
                    dataPhoto.getResourceId(i, -1),
                    i
                )
                listUser.add(user)
            }
            return listUser
        }

    private fun showRecyclerList() {
        rvUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        rvUsers.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intentToDetail = Intent(this@MainActivity, DetailUser::class.java)
                intentToDetail.putExtra("DATA",data)
                startActivity(intentToDetail)
            }
        })
    }
}