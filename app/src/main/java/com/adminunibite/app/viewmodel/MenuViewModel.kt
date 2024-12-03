package com.adminunibite.app.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adminunibite.app.domain.usecase.MenuUseCase
import com.adminunibite.app.model.AllMenuModel

class MenuViewModel(private val menuUseCase: MenuUseCase) : ViewModel() {

    //para subir Elementos
    private val _uploadResult = MutableLiveData<Boolean>()
    val uploadResult: LiveData<Boolean> get() = _uploadResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun uploadMenuItem(foodName: String, foodPrice: String, foodDescription: String, foodIngredient: String, foodImageUri: Uri?) {
        menuUseCase.uploadMenuItem(foodName, foodPrice, foodDescription, foodIngredient, foodImageUri) { success, message ->
            if (success) {
                _uploadResult.value = true
            } else {
                _errorMessage.value = message ?: "Error desconocido"
            }
        }
    }

    // Para obtener los elementos del menú
    private val _menuItems = MutableLiveData<List<AllMenuModel>>()
    val menuItems: LiveData<List<AllMenuModel>> get() = _menuItems

    private val _retrieveErrorMessage = MutableLiveData<String>()
    val retrieveErrorMessage: LiveData<String> get() = _retrieveErrorMessage

    fun getMenuItems() {
        menuUseCase.getMenuItems(
            onResult = { items ->
                _menuItems.value = items
            },
            onError = { message ->
                _retrieveErrorMessage.value = message
            }
        )
    }

}