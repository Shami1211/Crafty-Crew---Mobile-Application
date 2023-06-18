package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class InstructorLogin : AppCompatActivity() {

    private lateinit var instructorEmail:EditText
    private lateinit var instructorPassword:EditText

    private lateinit var instructorLogin: Button

    private lateinit var auth: FirebaseAuth

    private lateinit var pb:ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_login)

        auth = FirebaseAuth.getInstance()
        instructorEmail = findViewById(R.id.editTextTextPersonName4)
        instructorPassword = findViewById(R.id.editTextTextPersonName5)
        instructorLogin = findViewById(R.id.instructorLogin)
        pb = findViewById(R.id.progressBar4)

        pb.visibility = View.INVISIBLE

        instructorLogin.setOnClickListener{

            pb.visibility = View.VISIBLE

            var eInstructorEmail = instructorEmail.text.toString().trim()
            var eInstructorPassword = instructorPassword.text.toString().trim()

            if (TextUtils.isEmpty(eInstructorEmail)){
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
            }

            if (TextUtils.isEmpty(eInstructorPassword)){
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            }

            auth.signInWithEmailAndPassword(eInstructorEmail, eInstructorPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        pb.visibility = View.INVISIBLE
                        Toast.makeText(
                            baseContext,
                            "Authentication Success!",
                            Toast.LENGTH_SHORT,
                        ).show()
                        Log.d(ContentValues.TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        pb.visibility = View.INVISIBLE
                        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed!",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }
        }
    }
}