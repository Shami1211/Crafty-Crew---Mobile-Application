package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Instructor_dashboard : AppCompatActivity() {

    private lateinit var addNewInstructor_Button: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var instructorList: ArrayList<Instructor>

    private lateinit var dashboardPage: AppCompatButton
    private lateinit var payForInstructor: TextView

    private var db = Firebase.firestore

    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_dashboard)

        addNewInstructor_Button = findViewById(R.id.addNewInstructor_Button)
        dashboardPage = findViewById(R.id.dashboard)
        payForInstructor = findViewById(R.id.textView5)

        recyclerView= findViewById(R.id.instructorList)
        recyclerView.layoutManager=LinearLayoutManager(this)

        instructorList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        addNewInstructor_Button.setOnClickListener{
            val intent = Intent(this, Instructor_Add::class.java)
            startActivity(intent)
            finish()
        }

        dashboardPage.setOnClickListener{
            val intent = Intent(this, Dashbord_view::class.java)
            startActivity(intent)
            finish()
        }

        payForInstructor.setOnClickListener{
            val intent = Intent(this, Pay_instructor::class.java)
            startActivity(intent)
            finish()
        }

        db.collection("instructor").get()

            .addOnSuccessListener {

                if (!it.isEmpty){
                    for (data in it.documents) {
                        val instructor: Instructor? = data.toObject(Instructor::class.java)

                            if (instructor != null) {
                                instructorList.add(instructor)
                            }

                    }
                    recyclerView.adapter = InstructorAdapter(instructorList,this,db)
                }

                Toast.makeText(this,"Success!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this,"Failed!", Toast.LENGTH_SHORT).show()
            }



        db.collection("instructors").get()
                      .addOnSuccessListener {
                          if(!it.isEmpty) {
                              for (data in it.documents){
                                  val instructor:Instructor?= data.toObject(Instructor::class.java)
                                  if(instructor!=null){
                                      instructorList.add(instructor)
                                  }
                              }
                              recyclerView.adapter = InstructorAdapter(instructorList, this,db)

                          }
                      }

                      .addOnFailureListener{
                          Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                      }
              }
          }

