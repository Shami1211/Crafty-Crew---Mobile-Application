package com.example.myapplication

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.ref.Reference

class Item_Details_Activity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var sellerList: ArrayList<Seller>
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    private lateinit var goEvent: Button
    private lateinit var addItem: TextView

    private lateinit var builder: AlertDialog.Builder

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        recyclerView= findViewById(R.id.sellItemsList)

        goEvent = findViewById(R.id.goEvent)
        addItem = findViewById(R.id.addItem)

        auth = FirebaseAuth.getInstance()

        val singleSellerID = auth.currentUser?.uid.toString()

        recyclerView.layoutManager=LinearLayoutManager(this)


        addItem.setOnClickListener {
            startActivity(Intent(this,activity_sell_item::class.java))
        }


        goEvent.setOnClickListener {
            startActivity(Intent(this,Buyer_Events::class.java))
        }


        sellerList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("sellerItemsBySellerID").document(singleSellerID).collection("singleSellerItems").get()


            .addOnSuccessListener {
                for (data in it.documents){
                    val seller:Seller? = data.toObject(Seller::class.java)
                    if (seller != null) {
                        sellerList.add(seller)
                    }
                }
                recyclerView.adapter =SellerAdapter(sellerList,this,db,singleSellerID)


            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener{
                Toast.makeText(this, it.toString() , Toast.LENGTH_SHORT).show()
            }
    }

    override fun onBackPressed() {

        builder = AlertDialog.Builder(this)

        builder.setTitle("Alert")
            .setMessage("Do you want to Logout?")
            .setCancelable(true)
            .setPositiveButton("LogOut"){ _: DialogInterface, _: Int ->
                val intent = Intent(this, Activity_Seller_Login::class.java)
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Cancel"){ dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .show()
    }
}