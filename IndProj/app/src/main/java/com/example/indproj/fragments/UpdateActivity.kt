package com.example.lab3app.fragments

import com.example.indproj.data.Product


interface UpdateActivity {
    fun setTitle(_title: String)
    fun setFragment(fragmentID: Int, product: Product?=null, flag: Boolean=true)
}