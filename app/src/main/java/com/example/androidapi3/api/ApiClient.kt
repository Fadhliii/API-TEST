package com.example.androidapi3.api

import com.example.androidapi3.api.services.ProductService
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://dummyjson.com/"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Create a lazy property that returns to the ProductService interface
    val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }
}