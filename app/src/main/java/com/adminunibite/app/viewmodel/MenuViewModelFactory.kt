package com.adminunibite.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adminunibite.app.domain.usecase.MenuUseCase

class MenuViewModelFactory(private val menuUseCase: MenuUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuViewModel(menuUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}