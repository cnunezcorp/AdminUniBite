package com.adminunibite.app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.adminunibite.app.databinding.ActivityOrderDetailsBinding
import com.adminunibite.app.model.OrderDetails
import com.adminunibite.app.ui.adapter.OrderDetailsAdapter

class OrderDetailsActivity : AppCompatActivity() {

    private val binding: ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }

    private var userName: String? = null
    private var phoneNumber: String? = null
    private var totalPrice: String? = null
    private var foodNames: ArrayList<String> = arrayListOf()
    private var foodImages: ArrayList<String> = arrayListOf()
    private var foodQuantity: ArrayList<Int> = arrayListOf()
    private var foodPrices: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButton.setOnClickListener {
            finish()
        }
        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        val receivedOrderDetails = intent.getSerializableExtra("UserOrderDetails") as? OrderDetails
        receivedOrderDetails?.let { orderDetails ->
            userName = receivedOrderDetails.userName
            foodNames = receivedOrderDetails.foodNames as ArrayList<String>
            foodImages = receivedOrderDetails.foodImages as ArrayList<String>
            foodQuantity = receivedOrderDetails.foodQuantites as ArrayList<Int>
            phoneNumber = receivedOrderDetails.phoneNumber
            foodPrices = receivedOrderDetails.foodPrices as ArrayList<String>
            totalPrice = receivedOrderDetails.totalPrice

            setUserDetail()
            setAdapter()
        }


    }

    private fun setUserDetail() {
        binding.orderName.text = userName
        binding.orderPhone.text = phoneNumber
        binding.orderTotalPay.text = totalPrice
    }

    private fun setAdapter() {
        binding.orderDetailRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = OrderDetailsAdapter(this, foodNames, foodImages, foodQuantity, foodPrices)
        binding.orderDetailRecyclerView.adapter = adapter
    }
}