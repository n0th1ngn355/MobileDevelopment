package com.example.indproj.API

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

const val GET_CATEGORY = 10
const val APPEND_CATEGORY = 11
const val UPDATE_CATEGORY = 12
const val DELETE_CATEGORY = 13

const val GET_PRODUCT = 20
const val APPEND_PRODUCT = 21
const val UPDATE_PRODUCT = 22
const val DELETE_PRODUCT = 23

interface ProjAPI {
    @GET("?code=$GET_CATEGORY")
    fun getCategories(): Call<CategoryResponse>
    @Headers("Content-Type: application/json")
    @POST("category")
    fun postCategory(@Body postCategory: CategoryPost): Call<PostResult>

    @GET("?code=$GET_PRODUCT")
    fun getProducts(): Call<ProductResponse>
    @Headers("Content-Type: application/json")
    @POST("product")
    fun postProduct(@Body postProduct: ProductPost): Call<PostResult>

}
