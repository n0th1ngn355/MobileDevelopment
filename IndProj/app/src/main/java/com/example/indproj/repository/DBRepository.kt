package com.example.indproj.repository

import com.example.indproj.data.Category
import com.example.indproj.data.Product
import com.example.indproj.database.ProjDAO
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class DBRepository (val dao: ProjDAO): ProjDBRepository {
    override fun getAllCategories(): Flow<List<Category>> = dao.getAllCategories()

    override suspend fun insertCategory(category: Category) = dao.insertCategory(category)

    override suspend fun deleteCategory(category: Category) = dao.deleteCategory(category)

    override suspend fun deleteAllCategories() = dao.deleteAllCategories()

    override fun getAllProducts(): Flow<List<Product>> = dao.getAllProducts()

    override fun getCategoryProducts(categoryID: UUID): Flow<List<Product>> = dao.getCategoryProducts(categoryID)

    override suspend fun insertProduct(product: Product) = dao.insertProduct(product)

    override suspend fun deleteProduct(product: Product) = dao.deleteProduct(product)

    override suspend fun deleteAllProducts() = dao.deleteAllProducts()

}