package com.example.indproj.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "products",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])

data class Product(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name="product_name") val name: String = "",
    val brand: String = "",
    val availability: String = "",
    val fullDescription: String = "",
    @ColumnInfo(name = "category_id") val categoryId: UUID?= null
)