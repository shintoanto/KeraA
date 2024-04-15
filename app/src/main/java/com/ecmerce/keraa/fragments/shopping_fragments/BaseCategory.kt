package com.ecmerce.keraa.fragments.shopping_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.ecmerce.keraa.R
import com.ecmerce.keraa.adapter.BestProductAdaptr
import com.ecmerce.keraa.databinding.FragmentBaseCategoryBinding


open class BaseCategory : Fragment() {
    private lateinit var binding: FragmentBaseCategoryBinding
    protected val bestProductAdapter: BestProductAdaptr by lazy { BestProductAdaptr() }
    protected val offerAdapter: BestProductAdaptr by lazy { BestProductAdaptr() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseCategoryBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpOfferRv()
        setUpBestProductRv()


    }

    private fun setUpBestProductRv() {
        binding.rvOfferProducts.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = offerAdapter
        }
    }

    private fun setUpOfferRv() {
        binding.rvOfferProducts.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = offerAdapter
        }
    }


}