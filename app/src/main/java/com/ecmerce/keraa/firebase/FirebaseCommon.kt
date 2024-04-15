package com.ecmerce.keraa.firebase

import com.ecmerce.keraa.data.CartProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseCommon (
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    private val cartCollection =
        firestore.collection("user").document(firebaseAuth.uid!!).collection("cart")

    fun addProduct(cartProduct: CartProduct, onResult: (CartProduct?, Exception?) -> Unit) {
        cartCollection.document().set(cartProduct).addOnSuccessListener {
            onResult(cartProduct, null)
        }.addOnFailureListener {
            onResult(null, it)
        }
    }

    fun increaseQuantity(documentId: String, onResult: (String?, Exception?) -> Unit) {
        firestore.runTransaction { transaction ->
            val documentRef = cartCollection.document(documentId)
            val document = transaction.get(documentRef)
            val documentObj = document.toObject(CartProduct::class.java)
            documentObj?.let { cartProduct ->
                val documentQuantitiy = cartProduct.quantity + 1
                val newProductObj = cartProduct.copy(quantity = documentQuantitiy)
                transaction.set(documentRef, newProductObj)

            }
        }.addOnSuccessListener { onResult(documentId, null) }.addOnFailureListener {
            onResult(null, it)
        }

    }

    fun decreaseQuantity(documentId: String, onResult: (String?, Exception?) -> Unit) {
        firestore.runTransaction { transaction ->
            val documentRef = cartCollection.document(documentId)
            val document = transaction.get(documentRef)
            val documentObj = document.toObject(CartProduct::class.java)
            documentObj?.let { cartProduct ->
                val documentQuantitiy = cartProduct.quantity - 1
                val newProductObj = cartProduct.copy(quantity = documentQuantitiy)
                transaction.set(documentRef, newProductObj)

            }
        }.addOnSuccessListener { onResult(documentId, null) }.addOnFailureListener {
            onResult(null, it)
        }

    }

    enum class QuantityChanging { INCREASE, DECREASE }
}