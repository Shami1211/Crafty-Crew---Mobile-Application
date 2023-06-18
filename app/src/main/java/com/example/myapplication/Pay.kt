package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Pay : AppCompatActivity() {

    private lateinit var totalShow: TextView
    private lateinit var payNowButton: Button
    private lateinit var orderID: TextView

    private lateinit var cardName: EditText
    private lateinit var cardNumber: EditText
    private lateinit var cardDate: EditText
    private lateinit var cardCVV: EditText

    private lateinit var pb: ProgressBar

    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    val cardDetailsValidation = CardDetailsValidation()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        totalShow = findViewById(R.id.textView260)
        payNowButton = findViewById(R.id.button11)
        orderID = findViewById(R.id.textView290)
        pb = findViewById(R.id.progressBar9)

        cardName = findViewById(R.id.editTextTextPersonName2)
        cardNumber = findViewById(R.id.editTextTextPersonName7)
        cardDate = findViewById(R.id.editTextTextPersonName8)
        cardCVV = findViewById(R.id.editTextTextPersonName9)

        auth = FirebaseAuth.getInstance()

        pb.visibility = View.INVISIBLE

        totalShow.text = intent.getStringExtra("totalPrice").toString().toEditable()
        val t = intent.getStringExtra("totalPrice").toString().toEditable()

        payNowButton.text = "Pay Rs. $t"
        val oID = auth.currentUser?.uid.toString()
        orderID.text = oID

        payNowButton.setOnClickListener {

            val eCardName = cardName.text.toString().trim()
            val eCardNumber = cardNumber.text.toString().trim()
            val eCardDate = cardDate.text.toString().trim()
            val eCardCVV = cardCVV.text.toString().trim()

            if (cardDetailsValidation.cardFiledValidation(eCardName, eCardNumber, eCardDate, eCardCVV)){

                pb.visibility = View.VISIBLE

                val db = FirebaseFirestore.getInstance()
                val parentDocRef = db.collection("cart").document(oID)
                val subCollectionRef = parentDocRef.collection("singleUser")

                subCollectionRef.get()
                    .addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot) {
                            document.reference.delete()

                            pb.visibility = View.INVISIBLE
                            Toast.makeText(this, "Payment Success!", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, Payment_Successful::class.java)
                            intent.putExtra("paidPrice", payNowButton.text)
                            startActivity(intent)
                            finish()
                        }

                    }
                    .addOnFailureListener {
                        pb.visibility = View.INVISIBLE
                        Toast.makeText(this, "Payment Failed! Try Again!", Toast.LENGTH_SHORT).show()

                    }
            }else{
                pb.visibility = View.INVISIBLE
                Toast.makeText(this, "Try Again!!", Toast.LENGTH_SHORT).show()
            }


        }

    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}