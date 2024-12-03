package com.adminunibite.app.domain.usecase

import android.net.Uri
import com.adminunibite.app.data.MenuRepository
import com.adminunibite.app.model.AllMenuModel

class MenuUseCase(private val menuRepository: MenuRepository) {

    //Para añadir elementos
    fun uploadMenuItem(foodName: String, foodPrice: String, foodDescription: String, foodIngredient: String, foodImageUri: Uri?, onResult: (Boolean, String?) -> Unit) {
        menuRepository.uploadMenuItem(foodName, foodPrice, foodDescription, foodIngredient, foodImageUri, onResult)
    }

    // Obtener elementos
    fun getMenuItems(onResult: (List<AllMenuModel>) -> Unit, onError: (String) -> Unit) {
        menuRepository.getMenuItems(onResult, onError)
    }
}