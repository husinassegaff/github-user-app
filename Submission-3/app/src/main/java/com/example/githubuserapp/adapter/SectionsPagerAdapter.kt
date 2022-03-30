package com.example.githubuserapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserapp.fragment.FollowersFragment
import com.example.githubuserapp.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    companion object {

        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var usernameUser: String

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 ->  {
                fragment = FollowersFragment()
                val mBundle = Bundle()
                mBundle.putString(EXTRA_USERNAME, getUsernameUser())
                fragment.arguments = mBundle
            }
            1 -> {
                fragment = FollowingFragment()
                val mBundle = Bundle()
                mBundle.putString(EXTRA_USERNAME, getUsernameUser())
                fragment.arguments = mBundle
            }
        }

        return fragment as Fragment
    }

    fun setUsernameUser(dataUsername: String) {
        usernameUser = dataUsername
    }

    private fun getUsernameUser() : String = usernameUser

}