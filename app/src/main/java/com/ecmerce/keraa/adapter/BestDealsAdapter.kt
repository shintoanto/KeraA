package com.ecmerce.keraa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecmerce.keraa.R
import com.ecmerce.keraa.data.Product
import com.ecmerce.keraa.databinding.BestDealsRvItemBinding

class BestDealsAdapter :
    RecyclerView.Adapter<BestDealsAdapter.BestDealsAdapterViewHolder>() {

    inner class BestDealsAdapterViewHolder(private val binding: BestDealsRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(products: Product) {
            binding.apply {
                Glide.with(itemView).load(products.images.get(0)).error(R.drawable.ic_favorite)
                    .placeholder(R.drawable.ic_favorite)
                    .into(imgBestDeal)
                products.let {
                    val remainingPricePercentage = 1f - 1
                    val priceAfterOffer = remainingPricePercentage * products.price
                    tvNewPrice.text = "$ ${String.format("%.2f", priceAfterOffer)}"
                }
                tvOldPrice.text = "${products.price}"
                tvDealProductName.text = products.name
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BestDealsAdapter.BestDealsAdapterViewHolder {
        return BestDealsAdapterViewHolder(
            BestDealsRvItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BestDealsAdapterViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
        onClick?.invoke(product)
    }

    var onClick: ((Product) -> Unit)? = null

}

