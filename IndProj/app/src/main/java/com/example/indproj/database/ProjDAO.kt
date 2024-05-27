package com.example.indproj.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import com.example.indproj.data.Category
import com.example.indproj.data.Product

@Dao
interface ProjDAO {
    @Query("select * from Category order by name")
    fun getAllCategories(): Flow<List<Category>>

    @Insert(entity = Category::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Delete(entity = Category::class)
    suspend fun deleteCategory(category: Category)

    @Query("delete from Category")
    suspend fun deleteAllCategories()



    @Query("select * from Product order by name")
    fun getAllProducts(): Flow<List<Product>>

    @Query("select * from Product where categoryId=:categoryID")
    fun getCategoryProducts(categoryID: UUID): Flow<List<Product>>

    @Insert(entity = Product::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Delete(entity = Product::class)
    suspend fun deleteProduct(product: Product)

    @Query("delete from Product")
    suspend fun deleteAllProducts()

}