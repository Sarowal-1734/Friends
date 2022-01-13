package com.example.friends.adapters

import Results
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.friends.R
import com.example.friends.ui.fragments.user.UserFragmentDirections

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    var results: List<Results>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val result = results[position]
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
        holder.itemView.setOnClickListener(View.OnClickListener {
            val direction = UserFragmentDirections.actionUserFragmentToUserDetailsFragment(result)
            it.findNavController().navigate(direction)
        })
    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPortrait: ImageView = itemView.findViewById(R.id.imageViewPortrait)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewUserName)
        val textViewCountry: TextView = itemView.findViewById(R.id.textViewCountry)
    }

}