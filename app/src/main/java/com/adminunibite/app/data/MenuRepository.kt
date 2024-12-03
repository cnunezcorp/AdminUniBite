package com.adminunibite.app.data

import android.net.Uri
import com.adminunibite.app.model.AllMenuModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class MenuRepository(private val auth: FirebaseAuth, private val database: FirebaseDatabase) {

    // Método para subir un item
    fun uploadMenuItem(foodName: String, foodPrice: String, foodDescription: String, foodIngredient: String, foodImageUri: Uri?, onResult: (Boolean, String?) -> Unit) {
        val menuRef: DatabaseReference = database.getReference("menu")
        val newItemKey: String? = menuRef.push().key

        if (foodImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    val newItem = AllMenuModel(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription = foodDescription,
                        foodIngredient = foodIngredient,
                        foodImage = downloadUrl.toString()
                    )

                    newItemKey?.let {
                        menuRef.child(it).setValue(newItem).addOnSuccessListener {
                            onResult(true, "Datos cargados correctamente")
                        }
                    }
                }
                    .addOnFailureListener {
                        onResult(false, "Error al subir la imagen")
                    }
            }
                .addOnFailureListener {
                    onResult(false, "Fallo en la carga de datos")
                }
        } else {
            onResult(false, "Debe seleccionar una imagen")
        }
    }

    // Método para obtener los elementos del menú
    fun getMenuItems(onResult: (List<AllMenuModel>) -> Unit, onError: (String) -> Unit) {
        val menuRef = database.getReference("menu")
        menuRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val menuItems = mutableListOf<AllMenuModel>()
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(AllMenuModel::class.java)
                    menuItem?.let { menuItems.add(it) }
                }
                onResult(menuItems)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error.message)
            }
        })
    }
}