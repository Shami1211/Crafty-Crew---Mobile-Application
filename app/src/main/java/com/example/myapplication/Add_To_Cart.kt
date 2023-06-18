package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

class Add_To_Cart : AppCompatActivity() {

    private lateinit var topTitle: TextView
    private lateinit var topPrice: TextView
    private lateinit var description: TextView
    private lateinit var size: EditText
    private lateinit var quantity: TextView
    private lateinit var bottomPrice: TextView
    private lateinit var itemID:TextView

    private lateinit var plusQuantity:ImageButton
    private lateinit var minusQuantity:ImageButton

    private lateinit var image: ImageView

    private lateinit var addToCart: Button

    val addToCartValidation = Add_To_Cart_Validation()

    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)

        topTitle = findViewById(R.id.textView3)
        topPrice = findViewById(R.id.textView5)
        description = findViewById(R.id.checkBox)
        size = findViewById(R.id.checkBox4)
        quantity = findViewById(R.id.editTextTextPersonName14)
        bottomPrice = findViewById(R.id.textView9)
        itemID = findViewById(R.id.itemIDCart)
        image = findViewById(R.id.imageView9)

        plusQuantity = findViewById(R.id.imageButton4)
        minusQuantity = findViewById(R.id.imageButton3)

        auth = FirebaseAuth.getInstance()

        addToCart = findViewById(R.id.textView12)

        topTitle.text = intent.getStringExtra("iTitle").toString()
        topPrice.text = intent.getStringExtra("iPrice").toString()
        description.text = intent.getStringExtra("iDescription").toString()
        size.text = intent.getStringExtra("iSize").toString().toEditable()
        bottomPrice.text = intent.getStringExtra("iPrice").toString()
        itemID.text = intent.getStringExtra("itemID").toString()
        val imageUrl = intent.getStringExtra("image")
        Glide.with(this)
            .load(imageUrl)
            .into(image)

        val botPrice: Int = bottomPrice.text.toString().trim().toInt()
        println(botPrice)

        plusQuantity.setOnClickListener{

            val yourInteger: Int = quantity.text.toString().trim().toInt()
            println(yourInteger)

            val yourIntegerPrice: Int = bottomPrice.text.toString().trim().toInt()
            println(yourIntegerPrice)

            quantity.text = (yourInteger + 1).toString()

            bottomPrice.text = (yourIntegerPrice + botPrice).toString()
        }

        minusQuantity.setOnClickListener{

            val yourInteger: Int = quantity.text.toString().trim().toInt()
            println(yourInteger)

            val yourIntegerPrice: Int = bottomPrice.text.toString().trim().toInt()
            println(yourIntegerPrice)

            //Validation
            if (yourInteger == 1 || yourIntegerPrice == botPrice){
                Toast.makeText(this, "Check", Toast.LENGTH_SHORT).show()
            }else{

                quantity.text = (yourInteger -1).toString()

                bottomPrice.text = (yourIntegerPrice - botPrice).toString()
            }
        }

        addToCart.setOnClickListener {

            val cartID = auth.currentUser?.uid.toString()
            val cartRandomID = UUID.randomUUID().toString()

            val eTopTitle = topTitle.text.toString().trim()
            val eTopPrice = bottomPrice.text.toString().trim()
            val eDescription = description.text.toString().trim()
            val eSize = size.text.toString().trim()
            val eQuantity = quantity.text.toString().trim()

            if (addToCartValidation.addToCartValidateFields(eSize)){

                val cartMap = hashMapOf(
                    "cartItemID" to cartRandomID,
                    "cartItemTitle" to eTopTitle,
                    "cartItemPrice" to eTopPrice,
                    "cartItemDescription" to eDescription,
                    "cartItemSize" to eSize,
                    "cartItemQuantity" to eQuantity
                )

                db.collection("cart").document(cartID).collection("singleUser").document(cartRandomID).set(cartMap)
                    .addOnSuccessListener {
                        Toast.makeText(this,"Item Added To Cart!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Buyer_Items_List::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,"Item Added Failed!", Toast.LENGTH_SHORT).show()
                    }
            }else{

                Toast.makeText(this,"Enter Item Size!", Toast.LENGTH_SHORT).show()

            }


        }

    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)


}

