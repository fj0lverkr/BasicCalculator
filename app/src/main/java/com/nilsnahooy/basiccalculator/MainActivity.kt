package com.nilsnahooy.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var inputStringMap: MutableMap<String, String?> = mutableMapOf("firstVal" to "",
        "operator" to "", "secondVal" to "")
    private var inputString: String = ""
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
        when(btn.tag){
            "CLR" -> {
                resetInput("")
                result = 0.0
                resultTV?.text = ""
            }
            "+", "-", "*", "/" -> {
                if (operator != null) {
                    inputStringMap["secondVal"] = inputString
                    result = calculate(inputStringMap)
                    resetInput(result.toString())
                    inputStringMap["operator"] =  btn.tag as String
                    resultTV?.text = result?.toString() ?: "divide by zero error"
                }else{
                    if(inputStringMap["firstVal"] == "") resetInput(inputString)
                    inputStringMap["operator"] =  btn.tag as String
                    inputString = ""
                    resultTV?.text = btn.text
                }
                operator = btn.tag as String
            }
            "=" -> {
                inputStringMap["secondVal"] = inputString
                result = calculate(inputStringMap)
                resultTV?.text = result?.toString() ?: "divide by zero error"
                operator = null
                resetInput(result.toString())
            }
            else -> {
                inputString = if(inputString == "") {
                    btn.tag as String
                } else {
                    "$inputString${btn.tag as String}"
                }
                resultTV?.text = inputString
            }
        }
    }

    private fun calculate(input: Map<String, String?>): Double? {
        if (input.size == 3) {
            var firstNumber: Double? = input["firstVal"]?.toDoubleOrNull()
            var secondNumber: Double? = input["secondVal"]?.toDoubleOrNull()
            val operator: String? = input["operator"]
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

    private fun resetInput(firstVal:String){
        inputStringMap["firstVal"] = firstVal
        inputStringMap["operator"] = ""
        inputStringMap["secondVal"] = ""
        inputString = ""
    }
}