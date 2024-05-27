package com.example.indproj.API

import com.example.indproj.data.Category
import com.example.indproj.data.Product
import com.google.gson.annotations.SerializedName

class ProductPost (
    @SerializedName("action") val action: Int,
    @SerializedName("product") val product : Product
)