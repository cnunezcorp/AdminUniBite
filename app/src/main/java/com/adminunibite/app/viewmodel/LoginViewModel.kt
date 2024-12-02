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
import kotlin.math.log

class LoginViewModel : ViewModel() {
    private val auth : FirebaseAuth = Firebase.auth
    private val database : DatabaseReference = Firebase.database.reference
    val loginResult = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Log.d("Login", "Email: $email")
                Log.d("Login", "Password: $password")
                loginResult.postValue(true)
            } else {
                errorMessage.postValue("Falló el inicio de sesión")
                Log.d("Cuenta", "loguearCuenta: Fallo", task.exception)
            }
        }
    }

}