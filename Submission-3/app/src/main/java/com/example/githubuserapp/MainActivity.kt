package com.example.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.githubuserapp.fragment.FavoriteFragment
import com.example.githubuserapp.fragment.SettingFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, FavoriteFragment())
                    .addToBackStack(null)
                    .commit()
                return true
            }
            R.id.menu_dark -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SettingFragment())
                    .addToBackStack(null)
                    .commit()
                return true
            }
            else -> return true
        }
    }

}