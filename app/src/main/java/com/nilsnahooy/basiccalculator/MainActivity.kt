package com.nilsnahooy.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
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
                inputString = ""
                result = 0.0
                resultTV?.text = inputString
            }
            "+", "-", "X", "/" -> {
                if (operator != null) {
                    val op: String = operator as String
                    result = calculate(result, inputString, op)
                    resultTV?.text = result?.toString() ?: "divide by zero error"
                }else{
                    result = inputString?.toDoubleOrNull()
                    inputString = ""
                }
                operator = btn.text as String
                //inputString = operator
            }
            "=" -> {
                if (operator != null) {
                    val op: String = operator as String
                    result = calculate(result, inputString, op)
                    inputString = ""
                    resultTV?.text = result?.toString() ?: "divide by zero error"
                } else {
                    result = inputString?.toDoubleOrNull()
                    resultTV?.text = inputString ?: "0.0"
                    inputString = ""
                }
            }
            else -> {
                inputString = if((inputString == null || inputString == "")
                    && !btn.text.contains("([\\-+/X])+")) {
                    btn.text as String?
                } else if (btn.text.contains("([\\-+/X])+")) {
                    inputString
                } else {
                    "$inputString${btn.text as String}"
                }
                resultTV?.text = inputString
            }
        }
    }

    private fun calculate(result: Double?, input: String?, operator: String): Double? {
        var prev: Double? = result
        var inputDouble: Double? = input?.toDoubleOrNull()
        if (inputDouble == null){
           inputDouble = 0.0
        }
        if (prev == null) {
            prev = 0.0
        }
        return when(operator) {
            "+" -> prev + inputDouble
            "-" -> prev - inputDouble
            "/" -> {
                if(inputDouble != 0.0){
                    prev/inputDouble
                }else{
                    null
                }
            }
            else -> prev * inputDouble
        }
    }

}