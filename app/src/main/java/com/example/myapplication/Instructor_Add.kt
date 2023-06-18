package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firestore.v1.FirestoreGrpc.FirestoreImplBase
import java.util.UUID

class Instructor_Add : AppCompatActivity() {
    //created variable
    private lateinit var name_of_instructor: EditText
    private lateinit var email: EditText
    private lateinit var user_name: EditText
    private lateinit var password: EditText
    private lateinit var add_Instructor_button: Button
       //database instance variable
    private var db= Firebase.firestore

    val instructorAddvalidation = Instructor_AddValidation()

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_add)
           // assign to the varible to id
        auth = FirebaseAuth.getInstance()
        name_of_instructor = findViewById(R.id.name_of_instructor)
        email = findViewById(R.id.email)
        user_name = findViewById(R.id.user_name)
        password = findViewById(R.id.password)
        add_Instructor_button = findViewById(R.id.add_Instructor_button)
                // Click to the Button , affer datas goes to the database.
        add_Instructor_button.setOnClickListener(){

            val instructorid=UUID.randomUUID().toString()      //randam id genarated for database.

            val ename_of_instructor = name_of_instructor.text.toString().trim()
            val eemail = email.text.toString().trim()
            val euser_name = user_name.text.toString().trim()
            val epassword = password.text.toString().trim()

            if (instructorAddvalidation.instructorAddValidateFields(ename_of_instructor,eemail,euser_name,epassword)){

                val adminmap= hashMapOf(
                    "instructorid" to instructorid,
                    "iname" to ename_of_instructor,
                    "iemail" to eemail,
                    "iusername" to euser_name,
                    "ipassword" to epassword,
                )

                if (TextUtils.isEmpty(eemail)){
                    Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
                }

                if (TextUtils.isEmpty(epassword)){
                    Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
                }

                auth.createUserWithEmailAndPassword(eemail, epassword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(ContentValues.TAG, "createUserWithEmail:success")
                            val user = auth.currentUser

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()

                        }
                    }

                db.collection("instructors").document(instructorid).set(adminmap)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Instructor added  successfully", Toast.LENGTH_SHORT).show()
                        val i = Intent(this,Instructor_dashboard::class.java)
                        startActivity(i)
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "Instructor added  fail", Toast.LENGTH_SHORT).show()

                    }
            }else{

                Toast.makeText(this, "All Fields Required!!", Toast.LENGTH_SHORT).show()
            }



        }

    }
}