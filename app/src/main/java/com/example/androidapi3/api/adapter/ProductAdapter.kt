package com.example.androidapi3.api.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidapi3.R
import com.example.androidapi3.api.model.Product

class ProductAdapter(private val onClick: (Product) -> Unit) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductViewHolder.ProductCallBack) {
    class ProductViewHolder(itemView: View, val onClick: (Product) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val thumbnail = itemView.findViewById<ImageView>(R.id.img)
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val price = itemView.findViewById<TextView>(R.id.price)

        // Declare a nullable variable to hold the current product
        private var currentproduct: Product? = null

        // Initialize a block of code
        init {
            // Set an OnClickListener on the itemView
            itemView.setOnClickListener {
                // If currentproduct is not null, execute the onClick function with currentproduct as the argument
                currentproduct?.let {
                    onClick(it)
                }

            }

        }

        // Create a bind function that takes a product as an argument

        fun bind (product: Product)
        {

            currentproduct = product
            title.text = product.title
            price.text = product.price
            // Load the image from the product's thumbnail URL into the thumbnail ImageView
            // using Glide
            Glide.with(itemView)
                .load(product.thumbnail)
                .into(thumbnail)

        }

        object ProductCallBack : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
    // Create a ProductViewHolder class that extends RecyclerView.ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_product, parent, false)
        return ProductViewHolder(view, onClick)
    }
    // Implement the onBindViewHolder function
    // This function takes a ProductViewHolder and a position as arguments
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}