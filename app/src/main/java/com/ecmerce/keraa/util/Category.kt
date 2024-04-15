package com.ecmerce.keraa.util

sealed class Category(val category: String) {

    object Chair : Category("Chair")
    object Cupboard : Category("Cupboard")
    object Table : Category("Table")
    object Accessory : Category("Chair")
    object Furniture : Category("Category")
}