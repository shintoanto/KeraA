package com.ecmerce.keraa.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecmerce.keraa.data.CartProduct
import com.ecmerce.keraa.firebase.FirebaseCommon
import com.ecmerce.keraa.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartProductViewModel @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseCommon: FirebaseCommon
) : ViewModel() {

    private val _cartProduct = MutableStateFlow<Resource<CartProduct>>(Resource.Unspecified())
    val cartProduct = _cartProduct.asStateFlow()


    fun addToCartProduct(cartProduct: CartProduct) {
        firebaseFirestore.collection("user").document(firebaseAuth.uid!!).collection("cartproduct")
            .whereEqualTo("product.id", cartProduct.product.id).get().addOnSuccessListener {
                viewModelScope.launch {
                    if (it.documents.isEmpty()) { // add new product
                        addNewProduct(cartProduct)
                    } else {
                        val product = it.documents.first().toObject(CartProduct::class.java)
                        if (product == cartProduct) { // quantity increment
                            val doucId = it.documents.first().id
                            addQuantity(doucId, cartProduct)
                        } else {
                            addNewProduct(cartProduct)
                        }
                    }


                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _cartProduct.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    private fun addNewProduct(cartProduct: CartProduct) {
        firebaseCommon.addProduct(cartProduct) { cartProduct, exception ->
            if (exception == null) {
                viewModelScope.launch {
                    _cartProduct.emit(Resource.Success(cartProduct))
                }
            } else {
                viewModelScope.launch {
                    _cartProduct.emit(Resource.Error(exception.message.toString()))
                }
            }
        }
    }

    private fun addQuantity(documentId: String, cartProduct: CartProduct) {
        firebaseCommon.increaseQuantity(documentId) { docuId, exception ->
            if (exception == null) {
                viewModelScope.launch { _cartProduct.emit(Resource.Success(cartProduct)) }
            } else {
                viewModelScope.launch { _cartProduct.emit(Resource.Error(exception.message.toString())) }
            }
        }
    }
}
