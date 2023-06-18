package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class activity_Update_Item : AppCompatActivity() {

    private lateinit var itemID: TextView
    private lateinit var itemTitle: EditText
    private lateinit var itemPrice: EditText
    private lateinit var itemDescription: EditText
    private lateinit var itemSize: EditText
    private lateinit var randomID: TextView

    private lateinit var image:ImageView
    private lateinit var updateItemButton:Button

    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_item)

        itemID = findViewById(R.id.itemID)
        itemTitle = findViewById(R.id.editTextTextPersonName3)
        itemPrice = findViewById(R.id.editTextTextPersonName2)
        itemDescription = findViewById(R.id.editTextTextPersonName6)
        itemSize = findViewById(R.id.editTextTextPersonName5)
        updateItemButton = findViewById(R.id.updateItemButton)
        randomID = findViewById(R.id.randomIDUpdateItem)
        image = findViewById(R.id.imageView2)

        auth = FirebaseAuth.getInstance()

        randomID.text = intent.getStringExtra("randomID").toString().toEditable()
        itemID.text = intent.getStringExtra("itemID").toString().toEditable()
        itemTitle.text = intent.getStringExtra("itemTitle").toString().toEditable()
        itemPrice.text = intent.getStringExtra("itemPrice").toString().toEditable()
        itemSize.text = intent.getStringExtra("itemSize").toString().toEditable()
        itemDescription.text = intent.getStringExtra("itemDescription").toString().toEditable()
        val imageUrl = intent.getStringExtra("image")
        Glide.with(this)
            .load(imageUrl)
            .into(image)

        updateItemButton.setOnClickListener {

            val eItemTitle = itemTitle.text.toString().trim()
            val eItemPrice = itemPrice.text.toString().trim()
            val eItemDescription = itemDescription.text.toString().trim()
            val eItemCategory = itemSize.text.toString().trim()


            val sellermapUpdated = mapOf(
                "itemTitle" to eItemTitle,
                "ItemPrice" to eItemPrice,
                "ItemDescription" to eItemDescription,
                "ItemCategory" to eItemCategory
            )

            db.collection("seller").document(intent.getStringExtra("randomID").toString()).update(sellermapUpdated)
            db.collection("sellerItemsBySellerID").document(intent.getStringExtra("itemID").toString()).collection("singleSellerItems").document(intent.getStringExtra("randomID").toString()).update(sellermapUpdated)
                .addOnSuccessListener {
                    Toast.makeText(this, "Seller Item updated successfully", Toast.LENGTH_SHORT)
                        .show()

                    val i = Intent(this, Item_Details_Activity::class.java)
                    startActivity(i)
                    finish()

                }
                .addOnFailureListener {
                    Toast.makeText(this, "Seller update fail", Toast.LENGTH_SHORT).show()

                }
        }

    }
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}