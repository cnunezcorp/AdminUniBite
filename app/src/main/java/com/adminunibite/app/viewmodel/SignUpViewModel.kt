package com.adminunibite.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adminunibite.app.domain.usecase.SignUpUseCase
import com.adminunibite.app.model.UserModel

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {
    val signUpResult = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun createAccount(userModel: UserModel, password: String) {
        signUpUseCase.createAccount(userModel, password) { success, error ->
            if (success) {
                signUpResult.postValue(true)
            } else {
                errorMessage.postValue(error ?: "Error desconocido")
            }
        }
    }
}