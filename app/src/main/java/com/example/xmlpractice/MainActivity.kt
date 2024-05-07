package com.example.xmlpractice

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.xmlpractice.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.input
import kotlinx.android.synthetic.main.activity_main.one
import kotlinx.android.synthetic.main.activity_main.resulttv
import kotlinx.android.synthetic.main.activity_main.view.one
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun onDigitClick(view: View) {
        if(stateError){
            binding.input.text = (view as Button).text
            stateError = false
        }
        else{
            binding.input.append((view as Button).text)
        }
        lastNumeric = true
    }


    fun onEqualClick(view: View) {
        onEqual()
        binding.resulttv.text = binding.resulttv.text.toString()
    }


    fun onAllClearClick(view: View) {
         binding.input.text = ""
        binding.resulttv.text = ""
         lastNumeric = false
         stateError = false
         lastDot = false
        binding.resulttv.visibility = View.GONE
    }


    fun onOperationClick(view: View) {
        if(!stateError && lastNumeric){
            binding.input.append((view as Button).text)
           // lastDot = false
            lastNumeric = false
            onEqual()
        }
    }


    fun onBackClick(view: View) {
        binding.input.text = binding.input.text.toString().dropLast(1)
        try{
            var lastChar = binding.input.text.toString().last()
            if(lastChar.isDigit()){
                onEqual()
            }
        }catch (e: Exception){
            binding.resulttv.text = ""
            binding.resulttv.visibility = View.GONE
            Log.e("last char error", e.toString())
        }
    }

    fun onEqual(){
        if(lastNumeric && !stateError){
            val txt = binding.input.text.toString()
            expression = ExpressionBuilder(txt).build()
            try{
                val result = expression.evaluate()
                binding.resulttv.visibility = View.VISIBLE
                binding.resulttv.text = result.toString()
            }catch (ex : ArithmeticException){
                Log.e("evaluate error", ex.toString())
                binding.resulttv.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }


}
