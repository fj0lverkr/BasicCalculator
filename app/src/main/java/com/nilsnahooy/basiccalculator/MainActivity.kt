package com.nilsnahooy.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var inputStringList: MutableList<String> = mutableListOf()
    private var inputString: String? = null
    private var result: Double? = null
    private var resultTV: TextView? = null
    private var operator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttons: ArrayList<View>? = findViewById<LinearLayoutCompat>(R.id.activity_wrapper)
            .touchables
        if (buttons != null) {
            for(b in buttons)
            {
                val currentButton = b as Button
                currentButton.setOnClickListener { v ->
                    handleClick(v)
                }
            }
        }
    }

    private fun handleClick(v:View) {
        resultTV = findViewById(R.id.calc_result_tv)
        val btn = v as Button
        when(btn.text){
            "CLR" -> {
                inputStringList = mutableListOf()
                result = 0.0
                inputString = null
                resultTV?.text = ""
            }
            "+", "-", "X", "/" -> {
                if (operator != null) {
                    inputStringList.add(2, inputString as String)
                    result = calculate(inputStringList)
                    inputStringList = mutableListOf()
                    inputStringList.add(0, result.toString())
                    inputStringList.add(1, btn.text as String)
                    resultTV?.text = result?.toString() ?: "divide by zero error"
                }else{
                    inputStringList.add(0,inputString as String)
                    inputStringList.add(1, btn.text as String)
                    inputString = null
                    resultTV?.text = operator
                }
                operator = btn.text as String
            }
            "=" -> {
                if (operator != null) {
                    inputStringList.add(2, inputString as String)
                    result = calculate(inputStringList)
                    inputString = null
                    inputStringList = mutableListOf()
                    inputStringList.add(0, result.toString())
                    resultTV?.text = result?.toString() ?: "divide by zero error"
                } else {
                    result = inputString?.toDoubleOrNull()
                    resultTV?.text = inputString ?: "0.0"
                }
            }
            else -> {
                inputString = if(inputString == null || inputString == "") {
                    btn.text as String?
                } else {
                    "$inputString${btn.text as String}"
                }
                resultTV?.text = inputString
            }
        }
    }

    private fun calculate(input: List<String>): Double? {
        if (input.size == 3) {
            var firstNumber: Double? = input[0].toDoubleOrNull()
            var secondNumber: Double? = input[2].toDoubleOrNull()
            val operator: String = input[1]
            if (firstNumber == null){
                firstNumber = 0.0
            }
            if (secondNumber == null) {
                secondNumber = 0.0
            }
            return when(operator) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "/" -> {
                    if(secondNumber != 0.0){
                        firstNumber/secondNumber
                    }else{
                        null
                    }
                }
                else -> firstNumber * secondNumber
            }
        } else {
            return null
        }
    }

}