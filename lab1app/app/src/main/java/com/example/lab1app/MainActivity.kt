package com.example.lab1app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var res : Float? = 0.0f
    var currentOperation : Char = '='
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttons = listOf<Button>(
            findViewById<Button>(R.id.b0),findViewById<Button>(R.id.b1), findViewById<Button>(R.id.b2), findViewById<Button>(R.id.b3), findViewById<Button>(R.id.b4),
            findViewById<Button>(R.id.b5), findViewById<Button>(R.id.b6), findViewById<Button>(R.id.b7), findViewById<Button>(R.id.b8), findViewById<Button>(R.id.b9))

        val del = findViewById<ImageButton>(R.id.del)
        val bc = findViewById<Button>(R.id.bc)

        val divide = findViewById<Button>(R.id.divide)
        val multiply = findViewById<Button>(R.id.multiply)
        val subtract = findViewById<Button>(R.id.subtract)
        val add = findViewById<Button>(R.id.add)

        val operand1 = findViewById<TextView>(R.id.operand1)
        val oper = findViewById<TextView>(R.id.oper)
        val inputF = findViewById<EditText>(R.id.inputField)

        for (b in buttons){
            b.setOnClickListener{ btn ->
                if ((btn as Button).text == "0" ){
                    if (!inputF.text.isNullOrEmpty()){
                        inputF.setText(inputF.text.toString() + btn.text)
                    }
                } else{
                    if (inputF.text.toString() != "0"){
                        inputF.setText(inputF.text.toString() + btn.text)
                    }else{
                        inputF.setText(btn.text)
                    }
                }
            }
        }

        val operations = listOf<Button>(add,divide, subtract, multiply)

        fun calc(a: Float, b: Float, operation: Char) : Float =
            when (operation) {
                '+' -> a+b
                '-' -> a-b
                '*' -> a*b
                '/' -> a/b
                else -> 0.0f
            }

        for (op in operations){
            op.setOnClickListener{btn ->
                if (inputF.text.isNullOrEmpty() && operand1.text.isNullOrEmpty())
                    return@setOnClickListener
                if (currentOperation == '='){
                    operand1.setText(inputF.text)
                    inputF.setText("")
                    oper.setText(op.text)
                    currentOperation = op.text.toString()[0]
                }else{
                    if (!inputF.text.isNullOrEmpty()){
                        var a = operand1.text.toString().toFloat()
                        var b = inputF.text.toString().toFloat()
                        operand1.setText(calc(a,b,currentOperation).toString())
                        inputF.setText("")
                        oper.setText(op.text.toString())
                        currentOperation = op.text.toString()[0]
                    }else {
                        oper.setText(op.text.toString())
                        currentOperation = op.text.toString()[0]
                    }
                }
            }
        }

        findViewById<Button>(R.id.equal).setOnClickListener{btn ->
            if (!inputF.text.isNullOrEmpty() && !operand1.text.isNullOrEmpty()){
                var a = operand1.text.toString().toFloat()
                var b = inputF.text.toString().toFloat()
                inputF.setText(calc(a,b,currentOperation).toString())
                operand1.setText("")
                oper.setText("")
                currentOperation = '='
            }
        }

        bc.setOnClickListener{
            inputF.setText("")
            operand1.setText("")
            oper.setText("")
            currentOperation = '='
        }

        del.setOnClickListener{
            val n = inputF.text.length
            if (n != 0){
                inputF.setText(inputF.text.delete(n-1, n))
            }
        }
        findViewById<Button>(R.id.bpoint).setOnClickListener{
            if (inputF.text.isNullOrEmpty()){
                inputF.setText("0.")
            }else{
                if (inputF.text.toString().firstOrNull{ it == '.'} == null){
                inputF.setText(inputF.text.toString() + '.')
                }
            }
        }
    }
}