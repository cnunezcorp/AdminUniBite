package com.adminunibite.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adminunibite.app.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val database: DatabaseReference = Firebase.database.reference
    val signUpResult = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun createAccount(userModel: UserModel, password: String) {
        if (password.length < 6) {
            errorMessage.value = "La contraseña debe contener al menos 6 caracteres"
            return
        }

        auth.createUserWithEmailAndPassword(userModel.email!!, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveUserData(userModel)
                    signUpResult.value = true
                } else {
                    errorMessage.value = task.exception?.message ?: "Error desconocido"
                    Log.d("Cuenta", "crearCuenta: Fallo", task.exception)
                }
            }
    }

    private fun saveUserData(userModel: UserModel) {
        val userId = auth.currentUser!!.uid
        database.child("user").child(userId).setValue(userModel)
    }
}