package com.adminunibite.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adminunibite.app.R
import com.adminunibite.app.databinding.ActivityLoginBinding
import com.adminunibite.app.model.UserModel
import com.adminunibite.app.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private val auth : FirebaseAuth = Firebase.auth
    private val loginViewModel: LoginViewModel by viewModels()

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUI()

    }

    private fun setupUI() {

        binding.loginButton.setOnClickListener{
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if(email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Debe Llenar Todos los Datos", Toast.LENGTH_LONG).show()
            }
            else{
                Log.d("Login", "Email: $email")
                Log.d("Login", "Password: $password")
                loginViewModel.login(email, password)
            }
        }

        observeViewModel()

        binding.dontHaveAccountButton.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        loginViewModel.loginResult.observe(this) { success ->
            if (success){
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                updateUI(auth.currentUser)
            } else {
                Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_LONG).show()
            }
        }

        loginViewModel.errorMessage.observe(this) {error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}