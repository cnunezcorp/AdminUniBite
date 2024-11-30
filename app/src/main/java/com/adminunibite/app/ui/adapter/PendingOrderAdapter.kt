package com.adminunibite.app.ui.adapter

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adminunibite.app.databinding.PendingOrderItemBinding

class PendingOrderAdapter(
    private val CustomerName:ArrayList<String>,
    private val Quantity:ArrayList<String>,
    private val FoodImage:ArrayList<Int>,
    private val context: Context)
    : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding = PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = CustomerName.size

    inner class PendingOrderViewHolder(private val binding: PendingOrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                customerName.text = CustomerName[position]
                pendingOrderQuantity.text = Quantity[position]
                orderFoodImageView.setImageResource(FoodImage[position])

                orderAcceptButton.apply {
                    if (!isAccepted){
                        text = "Aceptar"
                    }
                    else{
                        text = "Despachar"
                    }
                    setOnClickListener{
                        if (!isAccepted){
                            text = "Despachar"
                            isAccepted = true
                            showToast("Orden Aceptada")
                        }
                        else{
                            CustomerName.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Orden Despachada")
                        }
                    }
                }
            }
        }
       private fun showToast(message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}