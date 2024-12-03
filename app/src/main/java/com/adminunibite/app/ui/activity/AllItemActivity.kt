package com.adminunibite.app.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adminunibite.app.R
import com.adminunibite.app.data.MenuRepository
import com.adminunibite.app.databinding.ActivityAllItemBinding
import com.adminunibite.app.domain.usecase.MenuUseCase
import com.adminunibite.app.model.AllMenuModel
import com.adminunibite.app.ui.adapter.MenuItemAdapter
import com.adminunibite.app.viewmodel.MenuViewModel
import com.adminunibite.app.viewmodel.MenuViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {

    // Proveer la instancia de ViewModel usando el Factory
    private val menuViewModel: MenuViewModel by viewModels {
        MenuViewModelFactory(
            MenuUseCase(
                MenuRepository(
                    FirebaseAuth.getInstance(),
                    FirebaseDatabase.getInstance()
                )
            )
        )
    }

    private val binding : ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    private val databaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference.child("menu")
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

        // Observar cambios en los items del menú
        menuViewModel.menuItems.observe(this, Observer { menuItems ->
            setAdapter(menuItems)
        })

        // Observar mensajes de error
        menuViewModel.retrieveErrorMessage.observe(this, Observer { errorMessage ->
            Log.d("MenuError", errorMessage)
        })

        // Cargar los items del menú
        menuViewModel.getMenuItems()

        binding.backImageButton.setOnClickListener {
            finish()
        }

    }

    private fun setAdapter(menuItems: List<AllMenuModel>) {
        val adapter = MenuItemAdapter(this, ArrayList(menuItems), databaseReference) // Puedes pasar la referencia de la base de datos si es necesario
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = adapter
    }
}