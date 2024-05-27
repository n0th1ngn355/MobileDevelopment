package com.example.indproj.API

import com.example.indproj.data.Category
import com.google.gson.annotations.SerializedName

class CategoryResponse {
    @SerializedName("items") lateinit var items: List<Category>
}