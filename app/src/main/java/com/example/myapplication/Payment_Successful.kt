package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Payment_Successful : AppCompatActivity() {

    private lateinit var paidAmount: TextView
    private lateinit var goProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_successful)

        paidAmount = findViewById(R.id.editTextTextPersonName4)
        goProfile = findViewById(R.id.button12)

        val paid = intent.getStringExtra("paidPrice").toString()

        paidAmount.text = paid

        goProfile.setOnClickListener {
            val intent = Intent(this, Buyer_Items_List::class.java)
            startActivity(intent)
            finish()
        }
    }
}