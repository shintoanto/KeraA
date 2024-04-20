package com.ecmerce.keraa.activity


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ecmerce.keraa.R
import com.ecmerce.keraa.databinding.ActivityShoppingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShoppingActivity : AppCompatActivity() {

    val binding by lazy { ActivityShoppingBinding.inflate(layoutInflater) }

    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Find the NavHostFragment
//        val navigation =
//            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment

        //   binding.bottomNavigation.setupWithNavController(navigation)


    }
}

