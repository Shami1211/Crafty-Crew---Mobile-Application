package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Dashbord_view : AppCompatActivity() {

    private lateinit var amount: TextView

    private lateinit var instructors: AppCompatButton

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbord_view)

        amount = findViewById(R.id.totalAmount)
        instructors = findViewById(R.id.instructor)

        db = FirebaseFirestore.getInstance()

        val ref = db.collection("adminBankAccount").document("G6CYAI5rVMjI61bxzw7I")
        ref.get().addOnSuccessListener {
            if (it != null) {
                val eAmount = it.data?.get("amount")?.toString()

                amount.text = eAmount
                println(amount)
            }
        }

        instructors.setOnClickListener {
            startActivity(Intent(this, Instructor_dashboard::class.java))
            finish()
        }

        val barChart = findViewById<BarChart>(R.id.barChart)

        //get count from Event collection
        val query = db.collection("events")

        val countQuery = query.count()

        countQuery.get(AggregateSource.SERVER)

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Count fetched successfully
                    val snapshot = task.result

                    // Create data entries for the chart
                    val barEntries = ArrayList<BarEntry>()
                    barEntries.add(BarEntry(1f, snapshot.count.toFloat()))
                    //barEntries.add(BarEntry(2f, instructorCount.text))

                    // Create a data set with the entries and a label for the chart
                    val barDataSet = BarDataSet(barEntries, "Events")
                    barDataSet.color = Color.BLUE

                    // Add the data set to a list of data sets
                    val dataSets = ArrayList<IBarDataSet>()
                    dataSets.add(barDataSet)

                    // Create a data object with the data sets
                    val data = BarData(dataSets)

                    // Customize the chart
                    barChart.data = data
                    barChart.description.isEnabled = false
                    barChart.setFitBars(true)
                    barChart.animateY(1500)
                    barChart.invalidate()
                }
            }
    }


}