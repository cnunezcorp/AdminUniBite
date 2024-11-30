package com.adminunibite.app.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.adminunibite.app.R
import com.adminunibite.app.databinding.ActivityPendingOrderBinding
import com.adminunibite.app.databinding.PendingOrderItemBinding
import com.adminunibite.app.ui.adapter.PendingOrderAdapter
import com.adminunibite.app.ui.adapter.PickupAdapter

class PendingOrderActivity : AppCompatActivity() {

    private val binding : ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
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

        val orderedCustomerName = arrayListOf(
            "Cristofer Nuñez",
            "Lissy Heredia",
            "Enmanuel Domenech",
        )

        val orderedQuantity = arrayListOf(
            "3",
            "2",
            "5"
        )

        val orderedFoodImage = arrayListOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3
        )

        val adapter = PendingOrderAdapter(orderedCustomerName, orderedQuantity, orderedFoodImage, this )

        binding.pendingOrderRecyclerView.adapter = adapter
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}