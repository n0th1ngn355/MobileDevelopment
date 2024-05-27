package com.example.indproj.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID
import com.google.gson.annotations.SerializedName

@Entity(
    indices = [Index("id"), Index("name")]
)

data class Category(
    @PrimaryKey @SerializedName("id") val id: String = UUID.randomUUID().toString(),
    @SerializedName("category_name") var name: String = ""
)