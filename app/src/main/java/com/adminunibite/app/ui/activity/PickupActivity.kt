package com.adminunibite.app.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.adminunibite.app.R
import com.adminunibite.app.databinding.ActivityPickupBinding
import com.adminunibite.app.ui.adapter.PickupAdapter

class PickupActivity : AppCompatActivity() {

    private val binding : ActivityPickupBinding by lazy {
        ActivityPickupBinding.inflate(layoutInflater)
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

        binding.backImageButton.setOnClickListener{
            finish()
        }

        val customerName = arrayListOf(
            "Cristofer Nuñez",
            "Lissy Heredia",
            "Enmanuel Domenech",
        )

        val moneyStatus = arrayListOf(
            "Recibido",
            "No Recibido",
            "Pendiente"
        )

        val adapter = PickupAdapter(customerName, moneyStatus)
        binding.pickupRecyclerView.adapter = adapter
        binding.pickupRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}