package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditEventActivity : AppCompatActivity() {

    private lateinit var editEventTitle: EditText
    private lateinit var editEventPlace: EditText
    private lateinit var editEventDate: EditText
    private lateinit var editEventTime: EditText
    private lateinit var eventID: TextView
    private lateinit var saveEditedEvent: Button
    private lateinit var eventUserID: TextView

    private lateinit var progressBarEditEvent: ProgressBar

    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        editEventTitle = findViewById(R.id.editEventTitle)
        editEventPlace = findViewById(R.id.editEventPlace)
        editEventDate = findViewById(R.id.editEventDate)
        editEventTime = findViewById(R.id.editEventTime)
        saveEditedEvent = findViewById(R.id.saveEditedEvent)
        eventID = findViewById(R.id.eventID)
        eventUserID = findViewById(R.id.randomEventID)

        progressBarEditEvent = findViewById(R.id.progressBarEditEvent)

        auth = FirebaseAuth.getInstance()

        // on below line we are setting our message to our text view.
        editEventTitle.text = intent.getStringExtra("title").toString().toEditable()
        editEventPlace.text = intent.getStringExtra("place").toString().toEditable()
        editEventDate.text = intent.getStringExtra("date").toString().toEditable()
        editEventTime.text = intent.getStringExtra("time").toString().toEditable()
        eventID.text = intent.getStringExtra("id").toString()
        eventUserID.text = intent.getStringExtra("eventID").toString()

        progressBarEditEvent.visibility = View.INVISIBLE

        saveEditedEvent.setOnClickListener {

            if (editEventValidateFields()) {

                progressBarEditEvent.visibility = View.VISIBLE

                val eventUserID = eventUserID.text.toString()
                val eventID = eventID.text.toString()
                val editedTitle = editEventTitle.text.toString()
                val editedPlace = editEventPlace.text.toString()
                val editedDate = editEventDate.text.toString()
                val editedTime = editEventTime.text.toString()

                val editedEventMap = mapOf(
                    "title" to editedTitle,
                    "place" to editedPlace,
                    "date" to editedDate,
                    "time" to editedTime
                )

                db.collection("events").document(eventID).update(editedEventMap)
                db.collection("eventsByInstructorID").document(eventUserID)
                    .collection("singleEvents").document(eventID).update(editedEventMap)

                    .addOnSuccessListener {
                        progressBarEditEvent.visibility = View.INVISIBLE
                        Toast.makeText(this, "Event Edit Success!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
            }
        }
    }

     fun editEventValidateFields(): Boolean {
        val title = editEventTitle.text.toString().trim()
        val place = editEventPlace.text.toString().trim()
        val date = editEventTime.text.toString().trim()
        val time = editEventDate.text.toString().trim()

        if (title.isEmpty()) {
            editEventTitle.error = "Title is required"
            editEventTitle.requestFocus()
            return false
        }

        if (place.isEmpty()) {
            editEventPlace.error = "Place is required"
            editEventPlace.requestFocus()
            return false
        }

        if (date.isEmpty()) {
            editEventTime.error = "Date is required"
            editEventTime.requestFocus()
            return false
        }

        if (time.isEmpty()) {
            editEventDate.error = "Time is required"
            editEventDate.requestFocus()
            return false
        }

        return true
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}