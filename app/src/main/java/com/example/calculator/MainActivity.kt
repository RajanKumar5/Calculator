package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var lastNumeric = false
    var lastDecimal = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onDigit(view: View) {
        binding.tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        binding.tvInput.text = ""
        lastNumeric = false
        lastDecimal = false
    }

    fun onDecimal(view: View) {
        if (lastNumeric && !lastDecimal) {
            binding.tvInput.append(".")
            lastNumeric = false
            lastDecimal = true
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(binding.tvInput.text.toString())) {
            binding.tvInput.append((view as Button).text)
            lastNumeric = false
            lastDecimal = false
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = binding.tvInput.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if (tvValue.contains("-")) {
                    var splitValues = tvValue.split("-")
                    var valOne = splitValues[0]
                    var valTwo = splitValues[1]

                    if (!prefix.isEmpty()) {
                        valOne = prefix + valOne
                    }

                    binding.tvInput.text = removeZeroAfterDecimal((valOne.toDouble() - valTwo.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    var splitValues = tvValue.split("+")
                    var valOne = splitValues[0]
                    var valTwo = splitValues[1]

                    if (!prefix.isEmpty()) {
                        valOne = prefix + valOne
                    }

                    binding.tvInput.text = removeZeroAfterDecimal((valOne.toDouble() + valTwo.toDouble()).toString())
                } else if (tvValue.contains("*")) {
                    var splitValues = tvValue.split("*")
                    var valOne = splitValues[0]
                    var valTwo = splitValues[1]

                    if (!prefix.isEmpty()) {
                        valOne = prefix + valOne
                    }

                    binding.tvInput.text = removeZeroAfterDecimal((valOne.toDouble() * valTwo.toDouble()).toString())
                } else if (tvValue.contains("/")) {
                    var splitValues = tvValue.split("/")
                    var valOne = splitValues[0]
                    var valTwo = splitValues[1]

                    if (!prefix.isEmpty()) {
                        valOne = prefix + valOne
                    }

                    binding.tvInput.text = removeZeroAfterDecimal((valOne.toDouble() / valTwo.toDouble()).toString())
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDecimal(result: String): String{
        var value = result
        var isZeroAfterDecimal = true
        /*if(result.contains(".0")){
            value = result.substring(0, result.length - 2)
        }*/

        val decimalIndex = result.indexOf(".")
        for (i in (decimalIndex + 1) until result.length){
            if(result[i].toString() != "0"){
                isZeroAfterDecimal = false
                break
            }
        }

        if (isZeroAfterDecimal){
            value = result.substring(0, decimalIndex)
        }

        return value
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*")
                    || value.contains("+") || value.contains("-")
        }
    }

}