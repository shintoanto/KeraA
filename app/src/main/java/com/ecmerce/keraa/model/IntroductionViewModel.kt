package com.ecmerce.keraa.model

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecmerce.keraa.R
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _navigate = MutableStateFlow(0)
    var navigate: StateFlow<Int> = _navigate

    init {
        val isButtonClicked = sharedPreferences.getBoolean("IntroductionKey", false)
        val user = firebaseAuth.currentUser
        if (user != null) {
            viewModelScope.launch {
                _navigate.emit(23)
            }
        } else if (isButtonClicked) {
            viewModelScope.launch {
                _navigate.emit(R.id.action_introductionFragment_to_accountOptionsFragment)
            }

        } else {
            Unit
        }


    }

    fun startButtonClick() {
        sharedPreferences.edit().putBoolean("IntroductionKey", true).apply()
    }
}