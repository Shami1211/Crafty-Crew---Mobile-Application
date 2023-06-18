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


class Activity_Create_Account : AppCompatActivity() {
    //Declare Variable
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var createSellerAccount: Button
    private lateinit var pb:ProgressBar
    private lateinit var auth: FirebaseAuth


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        //assign XML file ID
        auth = FirebaseAuth.getInstance()
        email = findViewById(R.id.editTextTextPersonName4)
        password = findViewById(R.id.editTextTextPersonName6)
        createSellerAccount = findViewById(R.id.createSeller)
        pb = findViewById(R.id.progressBar6)
        pb.visibility = View.INVISIBLE
        createSellerAccount.setOnClickListener {

            pb.visibility = View.VISIBLE

            var eSignupEmail = email.text.toString().trim()
            var eSignupPassword = password.text.toString().trim()

            //Add Validation IN Email
            if (TextUtils.isEmpty(eSignupEmail)){
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
            }

            //Add Validation IN Password
            if (TextUtils.isEmpty(eSignupPassword)){
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            }

            auth.createUserWithEmailAndPassword(eSignupEmail, eSignupPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        // Sign in success, update UI with the signed-in user's information
                        Log.d(ContentValues.TAG, "createUserWithEmail:success")
                        pb.visibility = View.INVISIBLE
                        val user = auth.currentUser
                        startActivity(Intent(this,Activity_Seller_Login::class.java))
                        finish()

                    } else {

                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
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