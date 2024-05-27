package com.example.indproj.API

import com.example.indproj.data.Category
import com.google.gson.annotations.SerializedName

class CategoryPost (
    @SerializedName("action") val action: Int,
    @SerializedName("category") val category : Category
)