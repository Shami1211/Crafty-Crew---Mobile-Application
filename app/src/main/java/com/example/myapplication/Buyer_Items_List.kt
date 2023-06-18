package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Buyer_Items_List : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var buyerList: ArrayList<Buyer>
    private var db = Firebase.firestore

    private lateinit var uID: TextView
    private lateinit var userEmail: TextView
    private lateinit var logOut: TextView

    private lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_items_list)

        recyclerView= findViewById(R.id.buyerItemList)
        uID = findViewById(R.id.textView20)
        userEmail = findViewById(R.id.textView19)
        logOut = findViewById(R.id.textView36)

        auth = FirebaseAuth.getInstance()

        val userID = auth.currentUser?.uid.toString()
        val userMail = auth.currentUser?.email.toString()

        uID.text = userID!!.toEditable()
        userEmail.text = userMail.toEditable()

        recyclerView.layoutManager= LinearLayoutManager(this)

        buyerList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.market -> {
                    val intent = Intent(this@Buyer_Items_List, Buyer_Items_List::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.cart -> {
                    val intent = Intent(this@Buyer_Items_List, View_Cart::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.account -> {
                    val intent = Intent(this@Buyer_Items_List, BuyerAccountInformation::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }


        db.collection("seller").get()
            .addOnSuccessListener {
                for (data in it.documents){
                    val buyer:Buyer? = data.toObject(Buyer::class.java)
                    if (buyer != null) {
                        buyerList.add(buyer)
                    }
                }
                recyclerView.adapter =BuyerAdapter(buyerList, this )

                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this, it.toString() , Toast.LENGTH_SHORT).show()
            }

        logOut.setOnClickListener {
            val intent = Intent(this, SplashScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}