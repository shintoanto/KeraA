package com.ecmerce.keraa.fragments.shopping_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ecmerce.keraa.activity.LoginActivity
import com.ecmerce.keraa.adapter.HomeViewPagerAdapter
import com.ecmerce.keraa.databinding.FragmentHomeBinding
import com.ecmerce.keraa.util.showBottomNav
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var auth: FirebaseAuth
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
                2 -> tab.text = "Cupboard"
                3 -> tab.text = "Accessory"
                4 -> tab.text = "Furniture"
            }

        }.attach()

        binding.logOut.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
            auth.signOut()
        }
    }


    override fun onResume() {
        super.onResume()
        showBottomNav()
    }
}


