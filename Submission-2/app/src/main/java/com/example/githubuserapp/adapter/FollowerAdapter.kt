package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.databinding.ItemFollowersBinding
import com.example.githubuserapp.response.FollowerResponseItem

class FollowerAdapter(private val listFollower: ArrayList<FollowerResponseItem>) : RecyclerView.Adapter<FollowerAdapter.RecyclerViewHolder>() {

    fun setFollowerData(item: ArrayList<FollowerResponseItem>) {
        listFollower.clear()
        listFollower.addAll(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val binding = ItemFollowersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val (login, type, avatarUrl, id) = listFollower[position]

        with(holder.binding) {
            tvItemUsernameFollower.text = login
            tvItemTypeFollower.text = type
            tvItemIdFollower.text = id.toString()
            Glide.with(this.cardView.context)
                .load(avatarUrl)
                .circleCrop()
                .into(imgItemPhotoFollower)
        }

    }

    override fun getItemCount(): Int = listFollower.size

    class RecyclerViewHolder(var binding: ItemFollowersBinding) : RecyclerView.ViewHolder(binding.root)

}