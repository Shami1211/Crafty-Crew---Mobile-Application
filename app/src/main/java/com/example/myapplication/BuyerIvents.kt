package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BuyerIvents : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventList: ArrayList<Event>
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_ivents)

        recyclerView = findViewById(R.id.eventListForBuyer)
        recyclerView.layoutManager = LinearLayoutManager(this)

        eventList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("events").get()

            .addOnSuccessListener {

                if (!it.isEmpty){
                    for (data in it.documents) {
                        val event: Event? = data.toObject(Event::class.java)
                        if (event != null){
                            eventList.add(event)
                        }
                    }
                    recyclerView.adapter = EventAdapter(eventList)
                }

                Toast.makeText(this,"Success!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this,"Failed!", Toast.LENGTH_SHORT).show()
            }
    }
}