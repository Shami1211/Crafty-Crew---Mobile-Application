package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class Instructor_Delete_Update : AppCompatActivity() {

    private lateinit var iID:TextView
    private lateinit var iName:EditText
    private lateinit var iEmail:EditText

    private lateinit var updateInstructorButton:Button

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_delete_update)

        iID = findViewById(R.id.textView37)
        iName = findViewById(R.id.editTextTextPersonName)
        iEmail = findViewById(R.id.editTextTextPersonName2)
        updateInstructorButton = findViewById(R.id.updateInstructorButton)

        iID.text = intent.getStringExtra("iID").toString().toEditable()
        iName.text = intent.getStringExtra("iName").toString().toEditable()
        iEmail.text = intent.getStringExtra("iEmail").toString().toEditable()

        UpdateInstructor(intent.getStringExtra("iID").toString())

    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    fun UpdateInstructor(id: String){

        updateInstructorButton.setOnClickListener() {

            val ename_of_instructor = iName.text.toString().trim()
            val eemail = iEmail.text.toString().trim()


            val adminmapUpdated = mapOf(
                "iname" to ename_of_instructor,
                "iemail" to eemail
            )

            db.collection("instructors").document(id).update(adminmapUpdated)
                .addOnSuccessListener {
                    Toast.makeText(this, "Instructor updated successfully", Toast.LENGTH_SHORT)
                        .show()
                    val i = Intent(this, Instructor_dashboard::class.java)
                    startActivity(i)
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Instructor update fail", Toast.LENGTH_SHORT).show()

                }
        }
    }

}