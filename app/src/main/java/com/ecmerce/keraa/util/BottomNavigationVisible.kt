package com.ecmerce.keraa.util

import android.view.View
import androidx.fragment.app.Fragment
import com.ecmerce.keraa.R
import com.ecmerce.keraa.activity.ShoppingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNav() {
    (activity as ShoppingActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
        View.GONE
}

fun Fragment.showBottomNav() {
    (activity as ShoppingActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
        View.VISIBLE
}