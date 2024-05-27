package com.example.indproj.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.indproj.data.Category
import com.example.indproj.repository.ProjRepository

class CategoriesViewModel : ViewModel() {
    var categoryList: LiveData<List<Category>> = ProjRepository.getInstance().categoryList
    private var _category : Category? = null
    val category
        get() =_category

    init {

        ProjRepository.getInstance().category.observeForever{
            _category=it
        }
    }

    fun deleteCategory(){
        if(category != null)
            ProjRepository.getInstance().deleteCategory(category!!)
    }

    fun appendCategory(categoryName: String){
        val category= Category()
        category.name=categoryName
        ProjRepository.getInstance().newCategory(category)
    }

    fun updateCategory(categoryName: String){
        if(_category != null){
            _category!!.name=categoryName
            ProjRepository.getInstance().updateCategory(_category!!)
        }
    }

    fun setCurrentCategory(position: Int){
        if((categoryList.value?.size ?: 0) > position)
            categoryList.value?.let{ProjRepository.getInstance().setCurrentCategory(it.get(position))}
    }

    fun setCurrentCategory(category: Category){
        ProjRepository.getInstance().setCurrentCategory(category)
    }

    val getCategoryListPosition
        get()= categoryList.value?.indexOfFirst { it.id==category?.id } ?: -1
}