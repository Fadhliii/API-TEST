package com.example.androidapi3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.androidapi3.api.ApiClient
import com.example.androidapi3.api.adapter.ProductAdapter
import com.example.androidapi3.api.model.Product
import com.example.androidapi3.api.model.ProductResponse
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    private lateinit var RefreshLayout: SwipeRefreshLayout
    private lateinit var cardView: RecyclerView
    private lateinit var call: Call<ProductResponse>
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RefreshLayout = findViewById(R.id.Refresh_Layout)
        cardView = findViewById(R.id.recyclerView)
        adapter = ProductAdapter {
            // Handle the click event
                product ->
            productOnClick(product)
        }
        // Set the adapter
        cardView.adapter = adapter
        // Set the layout manager
        cardView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        RefreshLayout.setOnRefreshListener {
            getData()
        }

        getData()

    }

    private fun getData() {
        RefreshLayout.isRefreshing = true

        // Call the ProductService interface
        call = ApiClient.productService.getAll()
        // Execute the call
        call.enqueue(object : retrofit2.Callback<ProductResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ProductResponse>,
                response: retrofit2.Response<ProductResponse>
            ) {
                RefreshLayout.isRefreshing = false
                if (response.isSuccessful) {
//                    val data = response.body()
                    // Submit the list to the adapter
                    adapter.submitList(response.body()?.products)
                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                RefreshLayout.isRefreshing = false
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Create a function to handle the click event
    private fun productOnClick(product: Product) {
        // Handle the click event toast
        Toast.makeText(this, "Product ${product.title} clicked", Toast.LENGTH_SHORT).show()
    }


}