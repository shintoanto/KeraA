package com.ecmerce.keraa.fragments.shopping_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ecmerce.keraa.adapter.HomeViewPagerAdapter
import com.ecmerce.keraa.databinding.FragmentHomeBinding
import com.ecmerce.keraa.util.showBottomNav
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryFragment = arrayListOf<Fragment>(
            MainCategoryFragment(),
            ChaireFragment(),
            CupboardFragment(),
            TableFragment(),
            AccesoryFragment(),
            FurnitureFragment()
        )
        val adapterPager = HomeViewPagerAdapter(categoryFragment, childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapterPager



        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Home"
                1 -> tab.text = "Chair"
                2 -> tab.text = "Cupbaord"
                3 -> tab.text = "Accesory"
                4 -> tab.text = "Furniture"
            }

        }.attach()
    }

    override fun onResume() {
        super.onResume()
        showBottomNav()
    }
}


