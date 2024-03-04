package com.example.lab2app

import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    private val questionBank = listOf<Question>(
        Question(R.string.q1, true),
        Question(R.string.q2, false),
        Question(R.string.q3, false),
        Question(R.string.q4, true),
        Question(R.string.q5, false)
    )
    private var currentIndex = 0
    var isCheater = false

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    fun moveToNext(){
        isCheater=false
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrev(){
        isCheater=false
        currentIndex = (questionBank.size + currentIndex - 1) % questionBank.size
    }
}