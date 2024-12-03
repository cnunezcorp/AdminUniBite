package com.adminunibite.app.domain.repository

import com.adminunibite.app.model.UserModel

interface AuthRepository {
    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit)
    fun signInWithGoogle(idToken: String, onResult: (Boolean, String?) -> Unit)
    fun createAccount(userModel: UserModel, password: String, onResult: (Boolean, String?) -> Unit)
}