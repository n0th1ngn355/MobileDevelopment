package com.example.indproj.API

import com.example.indproj.data.Product
import com.google.gson.annotations.SerializedName

class ProductResponse {
    @SerializedName("items") lateinit var items: List<Product>
}