package com.example.indproj.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.indproj.API.APPEND_CATEGORY
import com.example.indproj.API.APPEND_PRODUCT
import com.example.indproj.API.CategoryPost
import com.example.indproj.API.CategoryResponse
import com.example.indproj.API.DELETE_CATEGORY
import com.example.indproj.API.DELETE_PRODUCT
import com.example.indproj.API.PostResult
import com.example.indproj.API.ProductPost
import com.example.indproj.API.ProductResponse
import com.example.indproj.API.ProjAPI
import com.example.indproj.API.ProjConnection
import com.example.indproj.API.UPDATE_CATEGORY
import com.example.indproj.API.UPDATE_PRODUCT
import com.example.indproj.ApplicationIndiv
import com.example.indproj.database.ProjDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.example.indproj.data.Category
import com.example.indproj.data.Product
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response


const val TAG = "com.example.indproj.TAG"

class ProjRepository private constructor() {
    companion object {
        private var INSTANCE: ProjRepository? = null

        fun getInstance(): ProjRepository {
            if (INSTANCE == null) {
                INSTANCE = ProjRepository()
            }
            return INSTANCE
                ?: throw IllegalStateException("ProjRepository: Репозиторий не инициализирован")
        }
    }

    private val projDB by lazy {DBRepository(ProjDB.getDatabase(ApplicationIndiv.context).projDAO())}
    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)

    var category: MutableLiveData<Category> = MutableLiveData()
    val categoryList: LiveData<List<Category>> = projDB.getAllCategories().asLiveData()

    fun onDestroy(){
        myCoroutineScope.cancel()
    }


    fun setCurrentCategory(_category: Category) {
        category.postValue(_category)
    }

    fun setCurrentCategory(position: Int) {
        if (categoryList.value == null || position < 0 || (categoryList.value?.size!! <= position))
            return
        setCurrentCategory(categoryList.value!![position])
    }

    fun getCategoryPosition(category: Category): Int =
        categoryList.value?.indexOfFirst {
            it.id == category.id
        } ?: 1



    var productList: LiveData<List<Product>> = projDB.getAllProducts().asLiveData()
    var product: MutableLiveData<Product> = MutableLiveData()


    fun setCurrentProduct(_product: Product) {
        product.postValue(_product)
    }

    fun setCurrentProduct(position: Int) {
        if (productList.value == null || position < 0 || (productList.value?.size!! <= position))
            return
        setCurrentProduct(productList.value!![position])
    }

    fun getProductPosition(product: Product): Int = productList.value?.indexOfFirst {
        it.id == product.id
    } ?: 1




    private var projAPI = ProjConnection.getClient().create(ProjAPI::class.java)

    fun fetchCategory(){
        projAPI.getCategories().enqueue(object : retrofit2.Callback<CategoryResponse>{
            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка категорий", t)
            }

            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if (response.code() == 200){
                    val groups = response.body()
                    val items = groups?.items ?: emptyList()
                    Log.d(TAG, "Получен список категорий $items")
                    myCoroutineScope.launch {
                        projDB.deleteAllCategories()
                        for(f in items){
                            projDB.insertCategory(f)
                        }
                    }
                }else{
                    Log.d(TAG, "Ошибка получения списка категорий ${response.body()}")
                }
            }

        })
    }
    private fun updateCategory(categoryPost: CategoryPost){
        projAPI.postCategory(categoryPost)
            .enqueue(object: retrofit2.Callback<PostResult>{
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    if(response.code() == 200) {
                        fetchCategory()
                        Log.d(TAG, "Данные получены")
                    }
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    Log.d(TAG, "Ошибка изменения списка категорий", t)
                }
            })
    }

    fun newCategory(category: Category){
        updateCategory(CategoryPost(APPEND_CATEGORY, category))
    }
    fun deleteCategory(category: Category){
        updateCategory(CategoryPost(DELETE_CATEGORY, category))
    }
    fun updateCategory(category: Category){
        updateCategory(CategoryPost(UPDATE_CATEGORY, category))
    }


    fun fetchProduct(){
        projAPI.getProducts().enqueue(object : retrofit2.Callback<ProductResponse>{
            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка одежды", t)
            }

            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.code() == 200){
                    val products = response.body()
                    val items = products?.items ?: emptyList()
                    Log.d(TAG, "Получен список одежды $items")
                    myCoroutineScope.launch {
                        projDB.deleteAllProducts()
                        for(f in items){
                            projDB.insertProduct(f)
                        }
                    }
                }
            }

        })
    }
    private fun updateProduct(productPost: ProductPost){
        projAPI.postProduct(productPost)
            .enqueue(object: retrofit2.Callback<PostResult>{
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    if(response.code() == 200) {
                        fetchProduct()
                        Log.d(TAG, "Данные получены")
                    }
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    Log.d(TAG, "Ошибка изменения списка одежды", t)
                }
            })
    }

    fun newProduct(product: Product){
        updateProduct(ProductPost(APPEND_PRODUCT, product))
    }
    fun deleteProduct(product: Product){
        updateProduct(ProductPost(DELETE_PRODUCT, product))
    }
    fun updateProduct(product: Product){
        updateProduct(ProductPost(UPDATE_PRODUCT, product))
    }

    fun loadData(){
//        myCoroutineScope.launch {
//            projDB.deleteAllProducts()
//            projDB.deleteAllCategories()
//        }
        fetchCategory()
//        fetchProduct()
    }
}