package com.example.indproj.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.UUID


@Entity(indices = [Index("id"), Index("categoryId")])

data class Product(
    @PrimaryKey @SerializedName("id") val id: String = UUID.randomUUID().toString(),
    @SerializedName("product_name") var name: String = "",
    @SerializedName("description") var description: String? = null,
    @SerializedName("price") var price: Double = 0.0,
    @SerializedName("category_id") var categoryId: String = "",
    @SerializedName("stock_quantity") var stockQuantity: Int = 0,
    @SerializedName("manufacturer") var manufacturer: String = "",
    @SerializedName("country") var country: String = "",
    @SerializedName("sizes_available") var sizesAvailable: String = "",
    @SerializedName("color") var color: String = ""
)