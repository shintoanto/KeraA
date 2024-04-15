package com.ecmerce.keraa.fragments.shopping_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ecmerce.keraa.R
import com.ecmerce.keraa.model.CategoryViewModel
import com.ecmerce.keraa.util.BaseCategoryViewModelFactory
import com.ecmerce.keraa.util.Category
import com.ecmerce.keraa.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class FurnitureFragment: BaseCategory() {

    @Inject
    lateinit var firebasestore: FirebaseFirestore

    val viewModel by viewModels<CategoryViewModel> {
        BaseCategoryViewModelFactory(firestore = firebasestore, Category.Furniture)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.offerProducts.collectLatest {
                when (it) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        offerAdapter.differ.submitList(it.data)
                    }

                    is Resource.Error -> {

                    }

                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.bestProducts.collectLatest {
                when (it) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        bestProductAdapter.differ.submitList(it.data)
                    }

                    is Resource.Error -> {

                    }

                    else -> Unit
                }
            }
        }
    }
}