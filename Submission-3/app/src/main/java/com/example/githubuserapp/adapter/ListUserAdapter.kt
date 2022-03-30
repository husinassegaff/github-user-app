package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.databinding.ItemRowUserBinding
import com.example.githubuserapp.response.ItemsItem

class ListUserAdapter(private val listUser: ArrayList<ItemsItem>) : RecyclerView.Adapter<ListUserAdapter.RecyclerViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val (login, type, avatarUrl, id) = listUser[position]
        with(holder.binding) {
            tvItemUsernameUser.text = login
            tvItemTypeUser.text = type
            tvItemIdUser.text = id.toString()
            Glide.with(this.cardView.context)
                .load(avatarUrl)
                .circleCrop()
                .into(imgItemPhoto)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size

    class RecyclerViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}