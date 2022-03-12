package com.example.githubuserapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, username, description, location, avatar) = listUser[position]
        holder.tvName.text = name
        holder.tvUsername.text = username
        holder.tvDescription.text = description
        holder.tvLocation.text = location
        holder.imgPhoto.setImageResource(avatar)

    }

    override fun getItemCount(): Int = listUser.size


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName : TextView = itemView.findViewById(R.id.tv_item_name)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_item_username)
        var tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
        var tvLocation: TextView = itemView.findViewById(R.id.tv_item_location)
        var imgPhoto: ImageView =  itemView.findViewById(R.id.img_item_photo)

    }
}