package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.databinding.ItemFollowingBinding
import com.example.githubuserapp.response.FollowingResponseItem

class FollowingAdapter(private val listFollowing: ArrayList<FollowingResponseItem>) : RecyclerView.Adapter<FollowingAdapter.RecyclerViewHolder>() {

   fun setFollowingData(item: ArrayList<FollowingResponseItem>) {
       listFollowing.clear()
       listFollowing.addAll(item)
   }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val binding = ItemFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val (login, type, avatarUrl, id) = listFollowing[position]
        with(holder.binding) {
            tvItemUsernameFollowing.text = login
            tvItemTypeFollowing.text = type
            tvItemIdFollowing.text = id.toString()
            Glide.with(this.cardView.context)
                .load(avatarUrl)
                .circleCrop()
                .into(imgItemPhotoFollowing)
        }
    }

    override fun getItemCount(): Int = listFollowing.size

    class RecyclerViewHolder(var binding: ItemFollowingBinding) : RecyclerView.ViewHolder(binding.root)

}