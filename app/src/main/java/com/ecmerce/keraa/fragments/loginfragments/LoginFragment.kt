package com.ecmerce.keraa.fragments.loginfragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ecmerce.keraa.R
import com.ecmerce.keraa.activity.LoginActivity
import com.ecmerce.keraa.databinding.FragmentLoginBinding
import com.ecmerce.keraa.model.LoginViewModel
import com.ecmerce.keraa.util.Resource
import com.ecmerce.keraa.util.setUpBottomSheetDialogue
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogin.setOnClickListener {

                val email = edtEmail.text.toString().trim()
                val password = edtPassword.text.toString().trim()

                if (email.isBlank() && password.isBlank()) {
                    Toast.makeText(
                        requireContext(),
                        "Please fill required field",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.login(
                        email,
                        password
                    )
                }


            }

            tvForgotPassword.setOnClickListener {
                setUpBottomSheetDialogue { email ->
                    viewModel.resetPasswordViaEmail(email)
                }
            }
            tvSubTitName.setOnClickListener {
                findNavController().navigate(R.id.action_accountOptionsFragment_to_registerFragment)
            }

        }

        // collect the data after the submission
        lifecycleScope.launchWhenStarted {
            viewModel.login.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.btnLogin.startAnimation()
                    }

                    is Resource.Success -> {

                        binding.btnLogin.revertAnimation()
                        Intent(requireActivity(), LoginActivity::class.java).also { intent ->
                            // This flag will help previous activity not showing when press back button
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }

                    is Resource.Error -> {
                        binding.btnLogin.revertAnimation()
                        Log.d("Res", it.message.toString())
                        Toast.makeText(
                            requireContext(),
                            "Please check the fields you entered ${it.message.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> binding.btnLogin.revertAnimation()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resetPasswordEmail.collect {
                when (it) {
                    is Resource.Loading -> {
                        //binding.btnRegister.startAnimation();
                        Log.d("Res", "loading")
                    }

                    is Resource.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Mail send to the corespondent mail",
                            Toast.LENGTH_SHORT
                        ).show()
                        //   binding.btnRegister.revertAnimation();

                    }

                    is Resource.Error -> {
                        Log.d("Res", it.message.toString())
                        //  binding.btnRegister.revertAnimation();
                    }

                    else -> Unit
                }
            }
        }
    }
}