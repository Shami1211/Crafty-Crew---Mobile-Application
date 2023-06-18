package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Buyer_Events : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var buyerEventsList: ArrayList<EventToSeller>
    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_events)

        recyclerView= findViewById(R.id.buyerEventDisplay)

        recyclerView.layoutManager= LinearLayoutManager(this)

        buyerEventsList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("events").get()
            .addOnSuccessListener {
                for (data in it.documents){
                    val buyerEvents:EventToSeller? = data.toObject(EventToSeller::class.java)

                        if (buyerEvents != null) {
                            buyerEventsList.add(buyerEvents)
                        }

                }
                recyclerView.adapter = BuyerEventsAdapter(buyerEventsList)

                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this, it.toString() , Toast.LENGTH_SHORT).show()
            }

    }
}