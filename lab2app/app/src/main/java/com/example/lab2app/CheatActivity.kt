package com.example.lab2app

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

private const val EXTRA_ANSWER_IS_TRUE = "com.example.app2_352_2024.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.example.app2_352_2024.answer_shown"

class CheatActivity : AppCompatActivity() {

    companion object{
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent{
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
    private  var answerIsTrue = false
    private lateinit var showAnswerButton: Button
    private lateinit var answerTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent?.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)?:false

        setContentView(R.layout.activity_cheat)
        answerTextView = findViewById<TextView?>(R.id.tvAnswer).apply {
            visibility = View.INVISIBLE
            text = when{
                answerIsTrue -> "Верно"
                else -> "Ложь"
            }
        }

        showAnswerButton = findViewById(R.id.showBtn)

        showAnswerButton.setOnClickListener{
            answerTextView.visibility = View.VISIBLE
            showAnswerButton.visibility = View.GONE
            val data = Intent().apply {
                putExtra(EXTRA_ANSWER_SHOWN, true)
            }
            setResult(Activity.RESULT_OK, data)
        }

    }
}