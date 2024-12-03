package com.adminunibite.app.ui.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adminunibite.app.databinding.ItemItemBinding
import com.adminunibite.app.model.AllMenuModel
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenuModel>,
    databaseReference: DatabaseReference
) : RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {

    private val itemQuantities = IntArray(menuList.size) {1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuList.size

    inner class AddItemViewHolder(private val binding: ItemItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.apply {
                val menuItem = menuList[position]
                val uriString = menuItem.foodImage
                val uri = Uri.parse(uriString)
                foodNameTextView.text = menuItem.foodName
                foodPriceTextView.text = menuItem.foodPrice
                Glide.with(context).load(uri).into(foodImageView)
                quantityTextView.text = itemQuantities[position].toString()

                minusButton.setOnClickListener{
                    decreaseQuantity(position)
                }

                plusButton.setOnClickListener{
                    increaseQuantity(position)
                }
                deleteButton.setOnClickListener{
                    deleteQuantity(position)
                }
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10){
                itemQuantities[position] ++
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1){
                itemQuantities[position] --
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }

        private fun deleteQuantity(position: Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)
        }
    }
}