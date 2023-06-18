package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
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

class Register : AppCompatActivity() {

    private lateinit var signupEmail: EditText
    private lateinit var signupPassword: EditText

    private lateinit var signUpBtn: Button

    private lateinit var auth: FirebaseAuth

    private lateinit var pb: ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        signupEmail = findViewById(R.id.editTextTextPersonName3)
        signupPassword = findViewById(R.id.editTextTextPersonName4)

        signUpBtn = findViewById(R.id.createBuyerButton)

        pb = findViewById(R.id.progressBar2)

        pb.visibility = View.INVISIBLE

        signUpBtn.setOnClickListener {

            pb.visibility = View.VISIBLE

            var eSignupEmail = signupEmail.text.toString().trim()
            var eSignupPassword = signupPassword.text.toString().trim()

            if (TextUtils.isEmpty(eSignupEmail)){
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
            }

            if (TextUtils.isEmpty(eSignupPassword)){
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            }

            auth.createUserWithEmailAndPassword(eSignupEmail, eSignupPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        pb.visibility = View.INVISIBLE
                        val user = auth.currentUser
                        startActivity(Intent(this,Login::class.java))
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        pb.visibility = View.INVISIBLE
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }
        }

    }
}