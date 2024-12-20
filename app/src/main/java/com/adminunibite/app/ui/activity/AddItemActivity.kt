package com.adminunibite.app.ui.activity

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adminunibite.app.R
import com.adminunibite.app.data.MenuRepository
import com.adminunibite.app.databinding.ActivityAddItemBinding
import com.adminunibite.app.domain.usecase.MenuUseCase
import com.adminunibite.app.model.AllMenuModel
import com.adminunibite.app.viewmodel.MenuViewModel
import com.adminunibite.app.viewmodel.MenuViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngredient: String
    private var foodImageUri: Uri? = null

    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    // ViewModel y Factory
    private val viewModel: MenuViewModel by viewModels {
        MenuViewModelFactory(MenuUseCase(MenuRepository(FirebaseAuth.getInstance(), FirebaseDatabase.getInstance())))
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
        binding.addItemButtom.setOnClickListener {
            foodName = binding.foodNameEditText.text.toString().trim()
            foodPrice = binding.foodPriceEditText.text.toString().trim()
            foodDescription = binding.descriptionEditText.text.toString().trim()
            foodIngredient = binding.ingredientEditText.text.toString().trim()

            if (foodName.isNotBlank() && foodPrice.isNotBlank() && foodDescription.isNotBlank() && foodIngredient.isNotBlank()) {
                viewModel.uploadMenuItem(foodName, foodPrice, foodDescription, foodIngredient, foodImageUri)
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.selectImageTextView.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.backImageButton.setOnClickListener {
            finish()
        }
    }

    private fun setupObservers() {
        viewModel.uploadResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Se ha Agregado Correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.selectedImageView.setImageURI(uri)
            foodImageUri = uri
        }
    }
}