package com.adminunibite.app.ui.adapter

import android.content.Context
import android.net.Uri
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adminunibite.app.databinding.PendingOrderItemBinding
import com.adminunibite.app.model.OrderDetails
import com.bumptech.glide.Glide

class PendingOrderAdapter(
    private val context: Context,
    private val orderItems: MutableList<OrderDetails>,
    private val itemClicked: OnItemClicked,
) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    interface OnItemClicked {
        fun onItemClickListener(position: Int, orderDetails: OrderDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding = PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(orderItems[position], position)
    }

    override fun getItemCount(): Int = orderItems.size

    inner class PendingOrderViewHolder(private val binding: PendingOrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderDetails: OrderDetails, position: Int) {
            binding.apply {
                // Configura los datos visuales como el nombre, precio, imagen, etc.
                customerName.text = orderDetails.userName ?: "Cliente"
                pendingOrderQuantity.text = "${orderDetails.totalPrice ?: "0"}"
                val uri = Uri.parse(orderDetails.foodImages?.firstOrNull())
                Glide.with(context).load(uri).into(orderFoodImageView)

                // Configura el clic
                itemView.setOnClickListener {
                    itemClicked.onItemClickListener(position, orderDetails)
                }
            }
        }
    }
}