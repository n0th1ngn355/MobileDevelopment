package com.example.lab2app

import androidx.annotation.StringRes

data class Question(@StringRes val textResId : Int, val answer: Boolean)
