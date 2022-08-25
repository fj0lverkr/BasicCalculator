package com.nilsnahooy.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var inputPreview: String? = null
    private var result: Double? = null
    private var resultTV: TextView? = null

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
            "CLR" -> inputPreview = ""
            else -> {
                inputPreview = if(inputPreview == null || inputPreview == ""){
                    btn.text as String?
                }else{
                    "$inputPreview${btn.text as String?}"
                }
            }
        }
       resultTV?.text = inputPreview
    }
}