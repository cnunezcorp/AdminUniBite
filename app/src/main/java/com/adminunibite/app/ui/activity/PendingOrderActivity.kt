package com.adminunibite.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.adminunibite.app.OrderDetailsActivity
import com.adminunibite.app.R
import com.adminunibite.app.databinding.ActivityPendingOrderBinding
import com.adminunibite.app.databinding.PendingOrderItemBinding
import com.adminunibite.app.model.OrderDetails
import com.adminunibite.app.ui.adapter.PendingOrderAdapter
import com.adminunibite.app.ui.adapter.PickupAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrderActivity : AppCompatActivity(), PendingOrderAdapter.OnItemClicked {

    private val binding : ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    private var listOfName: MutableList<String> = arrayListOf()
    private var listOfTotalPrice: MutableList<String> = arrayListOf()
    private var listOfImageFirstFoodOrder: MutableList<String> = arrayListOf()
    private var listOfOrderItem: ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Firebase
        database = FirebaseDatabase.getInstance()
        databaseOrderDetails = database.reference.child("OrderDetails")

        getOrderDetails()

        binding.backImageButton.setOnClickListener{
            finish()
        }

    }

    private fun getOrderDetails() {
        databaseOrderDetails.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children) {
                    val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        // Loguea el contenido completo de cada pedido
                        Log.d("PendingOrderActivity", "Order Item: $it")
                        listOfOrderItem.add(it)
                    }
                }
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("PendingOrderActivity", "Error retrieving orders: ${error.message}")
            }
        })
    }

    private fun addDataToListForRecyclerView() {
        for (orderItem in listOfOrderItem) {
            // Itera y añade los datos a las listas que se usarán en el RecyclerView
            for (i in 0 until (orderItem.foodNames?.size ?: 0)) {
                orderItem.foodNames?.get(i)?.let { listOfName.add(it) }
                orderItem.foodPrices?.get(i)?.let { listOfTotalPrice.add(it) }
                orderItem.foodImages?.get(i)?.let { listOfImageFirstFoodOrder.add(it) }
            }
        }

        // Verificando
        Log.d("PendingOrderActivity", "Final Names: $listOfName")
        Log.d("PendingOrderActivity", "Final Prices: $listOfTotalPrice")
        Log.d("PendingOrderActivity", "Final Images: $listOfImageFirstFoodOrder")

        setAdapter()
    }

    private fun setAdapter() {
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PendingOrderAdapter(
            this,
            listOfOrderItem, // Pasa la lista completa
            object : PendingOrderAdapter.OnItemClicked {
                override fun onItemClickListener(position: Int, orderDetails: OrderDetails) {
                    // Aquí puedes abrir la pantalla de detalles con el pedido seleccionado
                    val intent = Intent(this@PendingOrderActivity, OrderDetailsActivity::class.java)
                    intent.putExtra("UserOrderDetails", orderDetails)
                    startActivity(intent)
                }
            }
        )
        binding.pendingOrderRecyclerView.adapter = adapter
    }

    override fun onItemClickListener(position: Int, orderDetails: OrderDetails) {
        val intent = Intent(this, OrderDetailsActivity::class.java)
        intent.putExtra("UserOrderDetails", orderDetails)
        startActivity(intent)
    }

}