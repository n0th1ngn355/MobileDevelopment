package com.example.indproj.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.indproj.data.Product
import com.example.indproj.data.Category
import com.example.indproj.repository.ProjRepository
import java.util.Date

class ProductListViewModel : ViewModel() {
    var productList: MutableLiveData<List<Product>> = MutableLiveData()

    private var _product: Product? = null
    var isNew: Boolean? = true

    val product
        get() = _product

    var category: Category? = null

    fun sortByName(){
        productList.postValue(
            productList.value?.sortedBy {
                it.name
            }
        )
    }
    fun sortByManufacturer(){
        productList.postValue(
            productList.value?.sortedBy {
                it.manufacturer
            }
        )
    }

    fun set_Category(category: Category){
        this.category = category
        ProjRepository.getInstance().productList.observeForever{
            productList.postValue(
                it.filter { it.categoryId == category.id } as MutableList<Product>
            )
        }
        ProjRepository.getInstance().product.observeForever{
            _product = it
        }
    }

    fun deleteProduct(){
        if(product != null){
            ProjRepository.getInstance().deleteProduct(product!!)
        }
    }



    fun setCurrentProduct(product: Product){
        ProjRepository.getInstance().setCurrentProduct(product)
    }

}