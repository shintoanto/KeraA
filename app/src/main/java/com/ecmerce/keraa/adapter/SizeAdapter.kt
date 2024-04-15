package com.ecmerce.keraa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ecmerce.keraa.databinding.SizeTvLayoutBinding

class SizeAdapter :
    RecyclerView.Adapter<SizeAdapter.SizeAdapterViewHolder>() {

    var selectedPosition = -1

    inner class SizeAdapterViewHolder(private val binding: SizeTvLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(size: String, position: Int) {
            binding.apply {
                binding.tvSize.text = size
                if (position == selectedPosition) {
                    binding.apply {
                        imgShadow.visibility = View.VISIBLE
                    }
                } else {
                    binding.apply {
                        imgShadow.visibility = View.INVISIBLE
                    }
                }


            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SizeAdapter.SizeAdapterViewHolder {
        return SizeAdapterViewHolder(
            SizeTvLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SizeAdapterViewHolder, position: Int) {
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

    var onItemClick: ((String) -> Unit)? = null

}