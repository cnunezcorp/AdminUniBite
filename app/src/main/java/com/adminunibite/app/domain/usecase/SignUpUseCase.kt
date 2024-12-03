package com.adminunibite.app.domain.usecase

import com.adminunibite.app.domain.repository.AuthRepository
import com.adminunibite.app.model.UserModel

class SignUpUseCase(private val repository: AuthRepository) {
    fun createAccount(userModel: UserModel, password: String, onResult: (Boolean, String?) -> Unit) {
        repository.createAccount(userModel, password, onResult)
    }
}