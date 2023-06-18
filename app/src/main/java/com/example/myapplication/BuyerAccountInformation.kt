package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class BuyerAccountInformation : AppCompatActivity() {

    private lateinit var myUserID: TextView
    private lateinit var myEmail: TextView

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_account_information)

        myUserID = findViewById(R.id.textView4109)
        myEmail = findViewById(R.id.textView410)

        auth = FirebaseAuth.getInstance()

        val userID = auth.currentUser?.uid.toString()
        val userMail = auth.currentUser?.email.toString()

        myUserID.text = userID!!.toEditable()
        myEmail.text = userMail.toEditable()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.market -> {
                    val intent = Intent(this@BuyerAccountInformation, Buyer_Items_List::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.cart -> {
                    val intent = Intent(this@BuyerAccountInformation, View_Cart::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.account -> {
                    val intent = Intent(this@BuyerAccountInformation, BuyerAccountInformation::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }

    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}