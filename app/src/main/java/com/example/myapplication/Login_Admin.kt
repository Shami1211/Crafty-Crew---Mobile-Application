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

class Login_Admin : AppCompatActivity() {

    private lateinit var adminEmail:EditText
    private lateinit var adminPassword:EditText

    private lateinit var adminLogin_button: Button

    private lateinit var auth: FirebaseAuth

    private lateinit var pb:ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)

        auth = FirebaseAuth.getInstance()
        adminLogin_button=findViewById(R.id.adminLogin_button)
        pb = findViewById(R.id.progressBar5)

        adminEmail = findViewById(R.id.editTextTextPersonName5)
        adminPassword = findViewById(R.id.editTextTextPersonName6)

        pb.visibility = View.INVISIBLE
        adminLogin_button.setOnClickListener(){

            pb.visibility = View.VISIBLE

            var eAdminEmail = adminEmail.text.toString().trim()
            var eAdminPassword = adminPassword.text.toString().trim()

            if (TextUtils.isEmpty(eAdminEmail)){
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
            }

            if (TextUtils.isEmpty(eAdminPassword)){
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            }

            auth.signInWithEmailAndPassword(eAdminEmail, eAdminPassword)
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
                        startActivity(Intent(this,Instructor_dashboard::class.java))
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