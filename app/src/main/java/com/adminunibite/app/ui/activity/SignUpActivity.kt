package com.adminunibite.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adminunibite.app.R
import com.adminunibite.app.databinding.ActivitySignUpBinding
import com.adminunibite.app.model.UserModel
import com.adminunibite.app.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private val viewModel: SignUpViewModel by viewModels()

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
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
        setupObservers()
        setupUI()

    }

    private fun setupUI() {
        binding.registerButton.setOnClickListener {
            val userModel = UserModel(
                name = binding.name.text.toString().trim(),
                coffeShopName = binding.coffeShopName.text.toString().trim(),
                email = binding.emailOrPhone.text.toString().trim(),
                //añadir la password aqui si quiero que se vea en la RealTime Database de Firebase
            )
            val password = binding.password.text.toString().trim()

            viewModel.createAccount(userModel, password)
        }
    }

    private fun setupObservers() {
        viewModel.signUpResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

}
