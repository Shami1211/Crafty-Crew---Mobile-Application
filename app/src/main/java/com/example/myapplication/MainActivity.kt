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
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var addNewEventBTN: TextView
    private lateinit var wolletFregment: Button
    private lateinit var builder: AlertDialog.Builder

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventList: ArrayList<Event>
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addNewEventBTN = findViewById(R.id.addNewEventBTN)
        wolletFregment = findViewById(R.id.wallet_fragment)

        recyclerView = findViewById(R.id.eventListForInstructor)
        recyclerView.layoutManager = LinearLayoutManager(this)

        auth = FirebaseAuth.getInstance()

        val userID = auth.currentUser?.uid.toString()

        eventList = arrayListOf()

        wolletFregment.setOnClickListener {
            val intent = Intent(this, WalletActivity::class.java)
            startActivity(intent)
            finish()
        }

        addNewEventBTN.setOnClickListener(){
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }

        db = FirebaseFirestore.getInstance()

        db.collection("eventsByInstructorID").document(userID).collection("singleEvents").get()

            .addOnSuccessListener {

                if (!it.isEmpty){
                    for (data in it.documents) {
                        val event: Event? = data.toObject(Event::class.java)
                        if (event != null){
                            eventList.add(event)
                        }
                    }
                    recyclerView.adapter = EventAdapterToInstructor(eventList, this,db,userID)
                }

                Toast.makeText(this,"Success!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this,"Failed!", Toast.LENGTH_SHORT).show()
            }
    }
    override fun onBackPressed() {

        builder = AlertDialog.Builder(this)

        builder.setTitle("Alert")
            .setMessage("Do you want to Logout?")
            .setCancelable(true)
            .setPositiveButton("LogOut"){ _: DialogInterface, _: Int ->
                val intent = Intent(this, MainDashboard::class.java)
                startActivity(intent)
            }
            .setNegativeButton("Cancel"){ dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .show()
    }
}