package com.ecmerce.keraa.model

import androidx.lifecycle.ViewModel
import com.ecmerce.keraa.data.Product
import com.ecmerce.keraa.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _specialProducts =
        MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    var specialProducts = _specialProducts.asStateFlow()

    private val _bestProductDeals = MutableStateFlow<Resource<Product>>(Resource.Unspecified())
    var bestProductDeals = _bestProductDeals.asStateFlow()


    init {
        bestProductDeals()
    }

    fun bestProductDeals() {

    }
}