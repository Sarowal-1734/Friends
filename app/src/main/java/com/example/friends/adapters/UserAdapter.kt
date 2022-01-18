package com.example.friends.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.friends.R
import com.example.friends.models.Result

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPortrait: ImageView = itemView.findViewById(R.id.imageViewPortrait)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewUserName)
        val textViewCountry: TextView = itemView.findViewById(R.id.textViewCountry)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_item_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val result = differ.currentList[position]
        holder.apply {
            // Get and set username
            val title = result.name.title
            val firstName = result.name.first
            val lastName = result.name.last
            val fullName = "$title  $firstName $lastName"
            textViewTitle.text = fullName
            // Get and set country
            val countryName = result.location.country
            textViewCountry.text = countryName
            // Get and set image
            val portrait = result.picture.large
            Glide.with(holder.itemView.context).load(portrait).into(imageViewPortrait)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(result) }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (Result) -> Unit) {
        onItemClickListener = listener
    }
}