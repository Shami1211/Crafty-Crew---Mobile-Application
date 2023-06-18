package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Cart_Item_Update : AppCompatActivity() {

    private lateinit var topTitle: TextView
    private lateinit var topPrice: TextView
    private lateinit var size: EditText
    private lateinit var quantity: TextView
    private lateinit var bottomPrice: TextView
    private lateinit var itemID: TextView

    private lateinit var plusQuantity: ImageButton
    private lateinit var minusQuantity: ImageButton

    private lateinit var updateBTN: Button

    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_item_update)

        topTitle = findViewById(R.id.textView3)
        topPrice = findViewById(R.id.textView5)
        size = findViewById(R.id.checkBox4)
        quantity = findViewById(R.id.editTextTextPersonName14)
        bottomPrice = findViewById(R.id.textView9)
        itemID = findViewById(R.id.itemIDCart)

        auth = FirebaseAuth.getInstance()

        plusQuantity = findViewById(R.id.imageButton4)
        minusQuantity = findViewById(R.id.imageButton3)

        updateBTN = findViewById(R.id.textView12)

        topTitle.text = intent.getStringExtra("cartItemTitle").toString()
        topPrice.text = intent.getStringExtra("cartItemPrice").toString()
        size.text = intent.getStringExtra("cartItemSize").toString().toEditable()
        quantity.text = intent.getStringExtra("cartItemQuantity").toString().toEditable()
        bottomPrice.text = intent.getStringExtra("cartItemPrice").toString().toEditable()
        itemID.text = intent.getStringExtra("cartItemID").toString()

        val userID = auth.currentUser?.uid.toString()

        val b1Price: Int = bottomPrice.text.toString().trim().toInt()
        val b2Price: Int = quantity.text.toString().trim().toInt()

        val botPrice: Int = b1Price / b2Price

        plusQuantity.setOnClickListener{

            val yourInteger: Int = quantity.text.toString().trim().toInt()
            println(yourInteger)

            val yourIntegerPrice: Int = bottomPrice.text.toString().trim().toInt()
            println(yourIntegerPrice)

            quantity.text = (yourInteger + 1).toString().toEditable()

            bottomPrice.text = (yourIntegerPrice + botPrice).toString()
        }

        minusQuantity.setOnClickListener{

            val yourInteger: Int = quantity.text.toString().trim().toInt()
            println(yourInteger)

            val yourIntegerPrice: Int = bottomPrice.text.toString().trim().toInt()
            println(yourIntegerPrice)

            if (yourInteger == 1 || yourIntegerPrice == botPrice){
                Toast.makeText(this, "Check", Toast.LENGTH_SHORT).show()
            }else{

                quantity.text = (yourInteger -1).toString().toEditable()

                bottomPrice.text = (yourIntegerPrice - botPrice).toString()
            }
        }

        updateBTN.setOnClickListener {

            val eItemTitle = topTitle.text.toString().trim()
            val eItemPrice = bottomPrice.text.toString().trim()
            val eItemSize = size.text.toString().trim()
            val eItemQuantity = quantity.text.toString().trim()

            val cartMap = mapOf(
                "cartItemTitle" to eItemTitle,
                "cartItemPrice" to eItemPrice,
                "cartItemSize" to eItemSize,
                "cartItemQuantity" to eItemQuantity
            )

            db.collection("cart").document(userID).collection("singleUser").document(intent.getStringExtra("cartItemID").toString()).update(cartMap)
                .addOnSuccessListener {
                    Toast.makeText(this,"Update Cart Success!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, View_Cart::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Update Cart Failed!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}