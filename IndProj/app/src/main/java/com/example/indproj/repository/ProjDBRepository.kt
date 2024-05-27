package com.example.indproj.repository

import com.example.indproj.data.Category
import com.example.indproj.data.Product
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ProjDBRepository {
    fun getAllCategories(): Flow<List<Category>>
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    suspend fun deleteAllCategories()



    fun getAllProducts(): Flow<List<Product>>
    fun getCategoryProducts(categoryID: UUID): Flow<List<Product>>
    suspend fun insertProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun deleteAllProducts()
}