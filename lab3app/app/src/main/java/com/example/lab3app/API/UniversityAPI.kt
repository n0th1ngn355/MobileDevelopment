package com.example.lab3app.API

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


const val GET_UNIVERSITY = 10
const val APPEND_UNIVERSITY = 11
const val UPDATE_UNIVERSITY = 12
const val DELETE_UNIVERSITY = 13

const val GET_FACULTY = 20
const val APPEND_FACULTY = 21
const val UPDATE_FACULTY = 22
const val DELETE_FACULTY = 23

interface UniversityAPI {
    @GET("?code=$GET_UNIVERSITY")
//    @GET("universities")
    fun getUniversities(): Call<UniversityResponse>
    @Headers("Content-Type: application/json")
    @POST("university")
    fun postUniversity(@Body postUniversity: UniversityPost): Call<PostResult>

    @GET("?code=$GET_FACULTY")
    fun getFaculties(): Call<FacultyResponse>
    @Headers("Content-Type: application/json")
    @POST("faculty")
    fun postFaculty(@Body postFaculty: FacultyPost): Call<PostResult>
}
