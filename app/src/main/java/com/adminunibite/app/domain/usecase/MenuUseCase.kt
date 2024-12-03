package com.adminunibite.app.domain.usecase

import android.net.Uri
import com.adminunibite.app.data.MenuRepository

class MenuUseCase(private val menuRepository: MenuRepository) {

    fun uploadMenuItem(foodName: String, foodPrice: String, foodDescription: String, foodIngredient: String, foodImageUri: Uri?, onResult: (Boolean, String?) -> Unit) {
        menuRepository.uploadMenuItem(foodName, foodPrice, foodDescription, foodIngredient, foodImageUri, onResult)
    }
}