package com.adminunibite.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adminunibite.app.R
import com.adminunibite.app.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private val binding: ActivitySignUpBinding by lazy{
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

        val universityList = arrayOf("Universidad Dominico Americano", "Universidad 2")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, universityList)
        val autoCompleteTextView = binding.listOfUniversity
        autoCompleteTextView.setAdapter(adapter)

        binding.registerButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.alreadyHaveAccountButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}