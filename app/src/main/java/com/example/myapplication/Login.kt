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
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {



    private lateinit var createAccount: TextView

    private lateinit var signInEmail:EditText
    private lateinit var signInPassword: EditText

    private lateinit var loginBtn: Button

    private lateinit var auth: FirebaseAuth

    private lateinit var pd:ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        signInEmail = findViewById(R.id.editTextTextPersonName12)
        signInPassword = findViewById(R.id.editTextTextPersonName13)

        pd = findViewById(R.id.progressBar3)

        loginBtn = findViewById(R.id.buyerLoginInPagebtn)

        createAccount = findViewById(R.id.textView29)

        pd.visibility = View.INVISIBLE

        createAccount.setOnClickListener{
            val i = Intent(this,Register::class.java)
            startActivity(i)
            finish()
        }

        loginBtn.setOnClickListener {

            pd.visibility = View.VISIBLE

            var eSignInEmail = signInEmail.text.toString().trim()
            var eSignInPassword = signInPassword.text.toString().trim()

            // Use Toast messages
            if (TextUtils.isEmpty(eSignInEmail)){
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
            }

            if (TextUtils.isEmpty(eSignInPassword)){
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            }

            auth.signInWithEmailAndPassword(eSignInEmail, eSignInPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        pd.visibility = View.INVISIBLE
                        Toast.makeText(
                            baseContext,
                            "Authentication Success!",
                            Toast.LENGTH_SHORT,
                        ).show()
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this,Buyer_Items_List::class.java))
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        pd.visibility = View.INVISIBLE
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
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