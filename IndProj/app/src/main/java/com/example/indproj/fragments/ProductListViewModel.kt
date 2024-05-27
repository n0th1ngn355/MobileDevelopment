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

//    fun appendProduct(lastName: String, firstName: String, middleName: String, birthDate: Date, phone: String, sex:Int){
//        val product=Product()
//        product.lastName=lastName
//        product.firstName=firstName
//        product.middleName=middleName
//        product.birthDate=birthDate
//        product.phone=phone
//        product.sex=sex
//        product.categoryID=category!!.id
//        ProjRepository.getInstance().newProduct(product)
//    }
//
//    fun updateProduct(lastName: String, firstName: String, middleName: String, birthDate: Date, phone: String, sex:Int){
//        if(_product!=null){
//            _product!!.lastName=lastName
//            _product!!.firstName=firstName
//            _product!!.middleName=middleName
//            _product!!.birthDate=birthDate
//            _product!!.phone=phone
//            _product!!.sex=sex
//            _product!!.categoryID=category!!.id
//            ProjRepository.getInstance().updateProduct(_product!!)
//        }
//    }

    fun setCurrentProduct(product: Product){
        ProjRepository.getInstance().setCurrentProduct(product)
    }

}