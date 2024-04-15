package com.ecmerce.keraa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecmerce.keraa.databinding.ViewPagerImageBinding

class ViewPager2Adapter : RecyclerView.Adapter<ViewPager2Adapter.ViewPager2AdapterViewHolder>() {

    inner class ViewPager2AdapterViewHolder(private val binding: ViewPagerImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: String) {
            Glide.with(itemView).load(image).into(binding.imageProductDetails)
        }
    }


    val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPager2Adapter.ViewPager2AdapterViewHolder {
        return ViewPager2AdapterViewHolder(
            ViewPagerImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ViewPager2Adapter.ViewPager2AdapterViewHolder,
        position: Int
    ) {
        val image = differ.currentList[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}