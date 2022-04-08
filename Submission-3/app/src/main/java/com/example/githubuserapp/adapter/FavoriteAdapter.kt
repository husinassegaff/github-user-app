package com.example.githubuserapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.database.Favorite
import com.example.githubuserapp.databinding.ItemRowUserBinding
import com.example.githubuserapp.helper.FavoriteDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val listFavorites = ArrayList<Favorite>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        Log.d("data", "Test masuk")
        this.onItemClickCallback = onItemClickCallback
    }

    fun setListFavorites(listFavorites: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listFavorites[holder.adapterPosition])
        }
    }


    inner class FavoriteViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                tvItemUsernameUser.text = favorite.username
                tvItemIdUser.text = favorite.followers.toString()
                tvItemTypeUser.text = favorite.repository.toString()
                Glide.with(this.cardView.context)
                    .load(favorite.avatar_url)
                    .circleCrop()
                    .into(imgItemPhoto)
            }


        }
    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Favorite)
    }
}