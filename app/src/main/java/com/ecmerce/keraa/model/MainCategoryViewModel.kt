package com.ecmerce.keraa.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecmerce.keraa.data.Product
import com.ecmerce.keraa.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _specialProducts =
        MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    var specialProducts = _specialProducts.asStateFlow()

    private val _bestProductDeals =
        MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    var bestProductDeals = _bestProductDeals.asStateFlow()

    private val _bestDeals =
        MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    var bestDeals = _bestDeals.asStateFlow()


    init {
        bestProductDeals()
        bestProductDeal()
        besDeals()
    }

    fun bestProductDeals() {

        viewModelScope.launch { _specialProducts.emit(Resource.Loading()) }
        firebaseFirestore.collection("Products").whereEqualTo("category", "Sneakers").get()
            .addOnSuccessListener { result ->
                Log.d("T", result.documents.toString())
                val products = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Success(products))
                }

            }.addOnFailureListener {
                viewModelScope.launch { _specialProducts.emit(Resource.Error(it.message.toString())) }
            }
    }

    fun besDeals() {

        viewModelScope.launch { _bestDeals.emit(Resource.Loading()) }
        firebaseFirestore.collection("Products").whereEqualTo("category", "Sneakers").get()
            .addOnSuccessListener { result ->
                Log.d("T", result.documents.toString())
                val products = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestDeals.emit(Resource.Success(products))
                }

            }.addOnFailureListener {
                viewModelScope.launch { _bestDeals.emit(Resource.Error(it.message.toString())) }
            }
    }

    fun bestProductDeal() {

        viewModelScope.launch { _bestProductDeals.emit(Resource.Loading()) }
        firebaseFirestore.collection("Products").whereEqualTo("category", "Sneakers").get()
            .addOnSuccessListener { result ->
                Log.d("T", result.documents.toString())
                val products = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestProductDeals.emit(Resource.Success(products))
                }

            }.addOnFailureListener {
                viewModelScope.launch { _bestProductDeals.emit(Resource.Error(it.message.toString())) }
            }
    }


}