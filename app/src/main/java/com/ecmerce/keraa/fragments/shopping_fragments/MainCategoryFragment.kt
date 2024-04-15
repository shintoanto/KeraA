package com.ecmerce.keraa.fragments.shopping_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecmerce.keraa.R
import com.ecmerce.keraa.adapter.BestDealsAdapter
import com.ecmerce.keraa.adapter.BestProductAdaptr
import com.ecmerce.keraa.adapter.SpecialProductsAdapter
import com.ecmerce.keraa.databinding.FragmentMainCategoryBinding
import com.ecmerce.keraa.model.MainCategoryViewModel
import com.ecmerce.keraa.util.Resource
import com.ecmerce.keraa.util.showBottomNav
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainCategoryFragment : Fragment() {
    private lateinit var binding: FragmentMainCategoryBinding
    private lateinit var specialProductsAdapter: SpecialProductsAdapter
    private lateinit var bestDealsAdapter: BestDealsAdapter
    protected val bestProductsAdapter: BestProductAdaptr by lazy { BestProductAdaptr() }
    protected val offerAdapter: BestProductAdaptr by lazy { BestProductAdaptr() }
    private val viewModel by viewModels<MainCategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

// adapter calling
        setUpSpecialProductsRv()
        setUpBestDealsProductsRv()
        setUpBestProducctsRv()

        specialProductsAdapter.onClick = {
            val bundle = Bundle().apply { putParcelable("product", it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetilsFragment, bundle)
        }

        bestDealsAdapter.onClick = {
            val bundle = Bundle().apply { putParcelable("product", it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetilsFragment, bundle)
        }

        bestProductsAdapter.onClick = {
            val bundle = Bundle().apply { putParcelable("product", it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetilsFragment, bundle)
        }


        // doing pagination
        binding.recyclerView3.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1) && dx != 0) {
                    onOfferPageRequest()
                }
            }
        })

        //
        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (v.getChildAt(0).bottom <= v.height + scrollY) {
                onBestProductPagingRequest()
            }
        })

        // pagination
        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (v.getChildAt(0).bottom <= v.height + scrollY) {
                viewModel.bestProductDeals
            }
        })


    }

    private fun onBestProductPagingRequest() {
        return
    }

    private fun onOfferPageRequest() {
        return
    }

    private fun setUpBestProducctsRv() {

        binding.recyclerView2.apply {
            adapter = bestProductsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }
    }

    private fun setUpBestDealsProductsRv() {
        bestDealsAdapter = BestDealsAdapter()
        binding.recyclerView3.apply {
            adapter = bestDealsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }
    }

    private fun hideLoading() {
        //
    }

    private fun showLoading() {
        //
    }

    private fun setUpSpecialProductsRv() {
        specialProductsAdapter = SpecialProductsAdapter()
        binding.recyclerView1.apply {
            adapter = specialProductsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.specialProducts.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
                        }

                        is Resource.Success -> {
                            Log.d("Res", it.data.toString())
                            specialProductsAdapter.differ.submitList(it.data)
                            hideLoading()

                        }

                        else -> {
                            Toast.makeText(
                                requireContext(),
                                it.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showBottomNav()
    }
}