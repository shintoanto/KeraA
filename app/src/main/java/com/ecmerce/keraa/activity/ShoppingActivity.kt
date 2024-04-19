package com.ecmerce.keraa.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ecmerce.keraa.R
import com.ecmerce.keraa.databinding.ActivityShoppingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShoppingActivity : AppCompatActivity() {

    val binding by lazy { ActivityShoppingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        findNavController(R.id.fragmentContainerView2).let {
//            binding.bottomNavigation.setupWithNavController(it)
//        }

        // Find the NavHostFragment
        // Find the NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(com.ecmerce.keraa.R.id.fragmentContainerView2) as NavHostFragment?

        // Get the NavController

        // Get the NavController
        val navController = navHostFragment!!.navController
        binding.bottomNavigation.setupWithNavController(navController)

    }

}
