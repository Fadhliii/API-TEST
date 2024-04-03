package com.example.androidapi3.api.services
//import product response
import com.example.androidapi3.api.model.ProductResponse
import retrofit2.Call
import retrofit2.http.GET


interface ProductService
{
    //get all product
    @GET("products")
    fun getAll(): Call<ProductResponse>
}