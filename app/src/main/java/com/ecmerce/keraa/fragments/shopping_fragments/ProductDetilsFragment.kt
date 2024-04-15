package com.ecmerce.keraa.fragments.shopping_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecmerce.keraa.R
import com.ecmerce.keraa.adapter.ColorsAdapter
import com.ecmerce.keraa.adapter.SizeAdapter
import com.ecmerce.keraa.adapter.ViewPager2Adapter
import com.ecmerce.keraa.data.CartProduct
import com.ecmerce.keraa.databinding.FragmentProductDetailBinding
import com.ecmerce.keraa.model.CartProductViewModel
import com.ecmerce.keraa.util.Resource
import com.ecmerce.keraa.util.hideBottomNav
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ProductDetilsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private val viewPagerAdapter by lazy { ViewPager2Adapter() }
    private val sizeAdapter by lazy { SizeAdapter() }
    private val colorAdapter by lazy { ColorsAdapter() }
    private val productData by navArgs<HomeFragmentArgs>()
    private var selectedSize: String? = null
    private var selectedColor: Int? = null
    private val viewModel by viewModels<CartProductViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater)

        hideBottomNav()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val products = productData.products

        binding.apply {
            productName.text = products.name
            productPrice.text = "${products.price}"
            productDescription.text = products.description

        }
        viewPagerAdapter.differ.submitList(products.images)
        products.colors.let {
            colorAdapter.differ.submitList(it)
        }
        products.sizes.let {
            sizeAdapter.differ.submitList(
                it
            )
        }

        sizeAdapter.onItemClick = {
            selectedSize = it
        }
        colorAdapter.onItemClick = {
            selectedColor = it
        }

        binding.btnLogin.setOnClickListener {
            viewModel.addToCartProduct(CartProduct(products, 1, selectedColor, selectedSize))
        }

        lifecycleScope.launch {
            viewModel.cartProduct.collectLatest {
                when (it) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {

                    }

                    is Resource.Error -> {

                    }

                    else -> Unit
                }
            }
        }
        pagerAdapter()
        colorAdapter()
        sizeAdapter()


    }

    private fun pagerAdapter() {
        binding.viewPagerProductImages.apply {
            viewPagerAdapter
        }
    }

    private fun colorAdapter() {
        binding.rvColor.apply {
            adapter = colorAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun sizeAdapter() {
        binding.rvSize.apply {
            adapter = sizeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}