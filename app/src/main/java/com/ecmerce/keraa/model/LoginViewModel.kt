package com.ecmerce.keraa.model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecmerce.keraa.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Mutable shared flow flow a single value emit
    private val _login = MutableSharedFlow<Resource<FirebaseUser>>()

    // asSharedFlow constructor change MutableSharedFlow into immutable shared flow
    val login = _login.asSharedFlow()

    private val _resetPasswordEmail = MutableSharedFlow<Resource<String>>()
    val resetPasswordEmail = _resetPasswordEmail.asSharedFlow()

    fun login(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            Log.d("email", "$email + $password")

            viewModelScope.launch { _login.emit(Resource.Loading()) }

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                viewModelScope.launch {
                    it.user?.let {
                        _login.emit(Resource.Success(it))
                        Log.d("Ress", it.metadata.toString())
                    }
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _login.emit(Resource.Error(it.message.toString()))
                }
            }
        }

    }

    fun resetPasswordViaEmail(email: String) {
        viewModelScope.launch {
            _resetPasswordEmail.emit(Resource.Loading())
        }

        viewModelScope.launch {
            firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener {
                viewModelScope.launch {
                    _resetPasswordEmail.emit(Resource.Success(email))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _resetPasswordEmail.emit(Resource.Error(it.message.toString()))
                }
            }
        }

    }
}