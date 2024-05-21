package com.example.indproj.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "categories",
    indices = [Index("id")])
data class Category(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "category_name") val name: String = ""
)