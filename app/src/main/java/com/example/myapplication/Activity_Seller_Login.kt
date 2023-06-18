package com.example.myapplication
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class Activity_Seller_Login : AppCompatActivity() {
    private lateinit var goSellerProfile: Button
    private lateinit var createSellerAccount: TextView
    private lateinit var pb:ProgressBar
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_login)

        auth = FirebaseAuth.getInstance()
        goSellerProfile = findViewById(R.id.goSellerProfile)
        pb = findViewById(R.id.progressBar7)
        email = findViewById(R.id.editTextTextPersonName9)
        password = findViewById(R.id.editTextTextPersonName10)
        createSellerAccount = findViewById(R.id.createSellerAccount)
        pb.visibility = View.INVISIBLE

        createSellerAccount.setOnClickListener(){
            val i = Intent(this,Activity_Create_Account::class.java)
            startActivity(i)
        }

        goSellerProfile.setOnClickListener(){

            pb.visibility = View.VISIBLE

            var eSignInEmail = email.text.toString().trim()
            var eSignInPassword = password.text.toString().trim()

            //validation part
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
                        pb.visibility = View.INVISIBLE
                        Toast.makeText(
                            baseContext,
                            "Authentication Success!",
                            Toast.LENGTH_SHORT,
                        ).show()
                        Log.d(ContentValues.TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this,Item_Details_Activity::class.java))
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