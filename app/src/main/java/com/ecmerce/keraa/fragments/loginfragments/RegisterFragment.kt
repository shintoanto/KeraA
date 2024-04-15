package com.ecmerce.keraa.fragments.loginfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ecmerce.keraa.R
import com.ecmerce.keraa.data.User
import com.ecmerce.keraa.databinding.FragmentRegisterBinding
import com.ecmerce.keraa.model.RegisterViewModel
import com.ecmerce.keraa.util.RegisterValidation
import com.ecmerce.keraa.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegisterFragment:Fragment(){

private lateinit var binding: FragmentRegisterBinding
private val viewModel by viewModels<RegisterViewModel>()

override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View {
    binding = FragmentRegisterBinding.inflate(inflater)
    return binding.root

}

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    binding.apply {
        btnRegister.setOnClickListener {
            edtFirstName.text.toString().trim().let { fName ->
                edtSecondName.text.toString().trim().let { sName ->
                    edtEmail.text.toString().trim().let { email ->
                        edtPassword.text.toString().trim().let { password ->
                            User(
                                firstName = fName,
                                lastName = sName,
                                email = email
                            ).let { user ->
                                viewModel.createAccountWithAEmailAndPassword(
                                    user = user, password = password
                                )
                            }


                        }
                    }
                }
            }
        }
        tvSubAccountToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionsFragment_to_loginFragment)
        }


    }

    lifecycleScope.launchWhenStarted {
        viewModel.register.collect {
            when (it) {
                is Resource.Loading -> {
                    binding.btnRegister.startAnimation()
                }

                is Resource.Success -> {
                    binding.btnRegister.revertAnimation()
                    Toast.makeText(
                        requireContext(),
                        it.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }

                is Resource.Error -> {
                    binding.btnRegister.revertAnimation()
                    Toast.makeText(
                        requireContext(),
                        it.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }

                else -> Unit
            }
        }
    }

    lifecycleScope.launchWhenStarted {
        viewModel.validation.collect { validation ->
            if (validation.email is RegisterValidation.Failed) {
                withContext(Dispatchers.Main) {
                    binding.edtEmail.apply {
                        requestFocus()
                        error = validation.email.message
                    }
                }
            }
            if (validation.password is RegisterValidation.Failed) {
                withContext(Dispatchers.Main) {
                    binding.edtPassword.apply {
                        requestFocus()
                        error = validation.password.message
                    }
                }
            }

        }
    }

}
}