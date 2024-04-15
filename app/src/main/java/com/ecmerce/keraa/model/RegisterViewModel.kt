package com.ecmerce.keraa.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ecmerce.keraa.data.User
import com.ecmerce.keraa.util.RegisterFieldState
import com.ecmerce.keraa.util.RegisterValidation
import com.ecmerce.keraa.util.Resource
import com.ecmerce.keraa.validateEmail
import com.ecmerce.keraa.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) :
    ViewModel() {

    private val _register = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val register: Flow<Resource<User>> = _register

    private val _validation = Channel<RegisterFieldState>()
    val validation = _validation.receiveAsFlow()

    fun createAccountWithAEmailAndPassword(user: User, password: String) {

        runBlocking { _register.emit(Resource.Loading()) }

        if (checkValidation(user, password)) {

            firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener { it ->
                    Log.d("Res", "success")
                    it.user?.let {
                        saveUserInfo(it.uid, user)
                        _register.value = Resource.Success(user)
                    }
                }.addOnFailureListener {
                    _register.value = Resource.Error(it.message.toString())

                }
        } else {
            val registrationField = RegisterFieldState(
                validateEmail(user.email),
                validatePassword(password)
            )
            runBlocking {
                _validation.send(registrationField)
            }
        }


    }

    // save user data to the Firestore
    private fun saveUserInfo(uId: String, user: User) {
        db.collection("user").document(uId).set(user).addOnSuccessListener {
            _register.value = Resource.Success(user)
        }.addOnFailureListener {
            _register.value = Resource.Error(it.message.toString())
        }
    }

    private fun checkValidation(user: User, password: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)
        val shouldRegister =
            emailValidation is RegisterValidation.Success && passwordValidation is RegisterValidation.Success
        return shouldRegister
    }

}