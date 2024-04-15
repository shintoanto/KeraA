package com.ecmerce.keraa.adapter

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ecmerce.keraa.databinding.ImageColorLayoutBinding

class ColorsAdapter :
    RecyclerView.Adapter<ColorsAdapter.ColorsAdapterViewHolder>() {

    var selectedPosition = -1

    inner class ColorsAdapterViewHolder(private val binding: ImageColorLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(colors: Int, position: Int) {
            binding.apply {
                val imageDrawable = ColorDrawable(colors)
                binding.imgColor.setImageDrawable(imageDrawable)
                if (position == selectedPosition) {
                    binding.apply {
                        imgShadow.visibility = View.VISIBLE
                        imgPicker.visibility = View.VISIBLE
                    }
                } else {
                    binding.apply {
                        imgShadow.visibility = View.INVISIBLE
                        imgColor.visibility = View.INVISIBLE
                    }
                }

            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ColorsAdapter.ColorsAdapterViewHolder {
        return ColorsAdapterViewHolder(
            ImageColorLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ColorsAdapterViewHolder, position: Int) {
        val color = differ.currentList[position]
        holder.bind(color, position)

        holder.itemView.setOnClickListener {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
            onItemClick?.invoke(color)
        }
    }

    var onItemClick: ((Int) -> Unit)? = null

}