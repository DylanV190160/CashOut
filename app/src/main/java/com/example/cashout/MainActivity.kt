package com.example.cashout

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Make FullScreen
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        //Get UI Elements
        val btnAdd = findViewById<Button>(R.id.btn_add)
        val btnRefresh = findViewById<Button>(R.id.btn_refresh)
        val tvLast = findViewById<TextView>(R.id.tv_last)
        val tvAmount = findViewById<TextView>(R.id.tv_amount)
        val etName = findViewById<EditText>(R.id.et_name)
        val etAmount = findViewById<EditText>(R.id.et_amount)
        //Set Shared Preferences
        val preferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val storeName = preferences.getString("store_key", "")
        var totalAmount: Int = preferences.getInt("amount_key", 0)
        //Get last saved storeName and totalAmount from SharedPreferences
        tvLast.text = "Last Amount Spent at $storeName"
        tvAmount.text = "$totalAmount"

        //Add Button OnClick Listener
        btnAdd.setOnClickListener {
            if(etName.text.toString().isEmpty() || etAmount.text.toString().isEmpty()){
                //Validation
                Toast.makeText(this, "Please enter Store Name & Amount", Toast.LENGTH_SHORT).show()
            } else {
                val editor = preferences.edit()
                editor.putString("store_key", etName.text.toString())
                tvLast.text = "Last Amount Spent at " + etName.text.toString()

                val sum = totalAmount + Integer.parseInt(etAmount.text.toString())
                editor.putInt("amount_key", sum)
                tvAmount.text = sum.toString()

                editor.apply()
                //Sum of total amounts added
                totalAmount = sum
                //Clear all input text from EditText
                etName.text.clear()
                etAmount.text.clear()
                //Validation
                Toast.makeText(this, "Input Captured Successful", Toast.LENGTH_SHORT).show()
            }
        }
        //Refresh Button OnClick Listener
        btnRefresh.setOnClickListener {
            //Clear SharedPreferences editor
            val editor = preferences.edit()
            editor.putString("store_key", null)
            editor.putInt("amount_key", 0)
            editor.apply()
            //Reset TextViews to defaults
            tvLast.text = "Last Amount Spent at None"
            tvAmount.text = "0"
            //Reset Sum of total amounts to zero
            totalAmount = 0
            //Clear all input text from EditText
            etName.text.clear()
            etAmount.text.clear()
            //Validation
            Toast.makeText(this, "Refresh Successful", Toast.LENGTH_SHORT).show()
        }
    }
}