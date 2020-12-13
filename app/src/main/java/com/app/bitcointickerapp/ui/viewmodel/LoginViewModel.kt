package com.app.bitcointickerapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bitcointickerapp.data.repository.CoinRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    application: Application
) : BaseViewModel(application) {
    fun signIn(email: String, password: String, onResult: (Task<AuthResult>) -> Unit) {
        viewModelScope.launch {
            coinRepository.signInFirebase(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    onResult(it)
                } else {
                    onResult(it)
                }
            }
        }
    }
}