package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class View_Cart : AppCompatActivity() {

    private lateinit var adapter: RecyclerView.Adapter<CartAdapter.CartViewHolder> // Define the adapter variable

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartItemList: ArrayList<Cart>
    private var db = Firebase.firestore

    private lateinit var checkOut: Button

    private lateinit var totalPrice:TextView

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_cart)

        totalPrice = findViewById(R.id.textView38)
        checkOut = findViewById(R.id.button7)

        recyclerView= findViewById(R.id.myCartItems)
        recyclerView.layoutManager= LinearLayoutManager(this)

        cartItemList = arrayListOf()

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        //Use Fragments
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.market -> {
                    val intent = Intent(this@View_Cart, Buyer_Items_List::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.cart -> {
                    val intent = Intent(this@View_Cart, View_Cart::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.account -> {
                    val intent = Intent(this@View_Cart, BuyerAccountInformation::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
        val userID = auth.currentUser?.uid.toString()

        // Step 1: Retrieve cart items from Firestore
        val cartItemsCollection = db.collection("cart").document(userID).collection("singleUser")
        val cartItemsQuery = cartItemsCollection.get()

        // Step 2: Create data model for cart items
        //data class Cart(val itemName: String, val itemPrice: Double, val quantity: Int)

        // Step 3: Calculate total cost of each item and sum up to get final total amount

        var totalAmount = 0
        cartItemsQuery.addOnSuccessListener { cartItems ->
            for (doc in cartItems) {
                val cartItem = doc.toObject(Cart::class.java)
                val itemPrice: Int = cartItem.cartItemPrice.toString().trim().toInt()
                totalAmount += itemPrice
            }
            // Step 4: Display final total amount in shopping cart page
            totalPrice.text = totalAmount.toString()
        }


        db.collection("cart").document(userID).collection("singleUser").get()
            .addOnSuccessListener {
                for (data in it.documents){
                    val cart:Cart? = data.toObject(Cart::class.java)
                    if (cart != null) {
                        cartItemList.add(cart)
                    }
                }
                recyclerView.adapter =CartAdapter(cartItemList, this,db,userID)


                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this, it.toString() , Toast.LENGTH_SHORT).show()
            }

        checkOut.setOnClickListener {
            val intent = Intent(this, Pay::class.java)
            intent.putExtra("totalPrice", totalPrice.text)
            startActivity(intent)
            finish()
        }
    }
}
