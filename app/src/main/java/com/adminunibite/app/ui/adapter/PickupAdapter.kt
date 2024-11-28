package com.adminunibite.app.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.adminunibite.app.R
import com.adminunibite.app.databinding.PickupItemBinding

class PickupAdapter(private val CustomerNames:ArrayList<String>, private val MoneyStatus:ArrayList<String>) : RecyclerView.Adapter<PickupAdapter.PickupViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickupViewHolder {
        val binding = PickupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PickupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PickupViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = CustomerNames.size

    inner class PickupViewHolder(private val binding: PickupItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.apply {
                customerName.text = CustomerNames[position]
                orderMoneyStatus.text = MoneyStatus[position]
                val colorMap = mapOf(
                    "Recibido" to Color.GREEN, "No Recibido" to Color.RED, "Pendiente" to Color.GRAY
                )
                orderMoneyStatus.setTextColor(colorMap[MoneyStatus[position]]?:Color.BLACK)
                orderStatusColor.backgroundTintList = ColorStateList.valueOf(colorMap[MoneyStatus[position]]?:Color.BLACK)
            }
        }
    }
}