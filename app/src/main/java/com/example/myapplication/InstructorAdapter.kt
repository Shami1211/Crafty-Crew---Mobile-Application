package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// single layout looping in instructorAdapter
class InstructorAdapter(
    private val instructorList: ArrayList<Instructor>,
    private val context: Context,
    private val db: FirebaseFirestore
    ):RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder>() {

    //single layout's id in here.
    class InstructorViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val instructorName: TextView=itemView.findViewById(R.id.instructorName)
        val instructorEmail:TextView=itemView.findViewById(R.id.instructerEmail)
        val instructorId:TextView=itemView.findViewById(R.id.instructerId)
        val updateImageButton: ImageButton = itemView.findViewById(R.id.updateButton)
        val deleteInstructor: ImageButton = itemView.findViewById(R.id.DeleteImageButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructorViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_single_instructor, parent, false)
        return InstructorViewHolder(itemView)
    }

    override fun getItemCount(): Int {
      return instructorList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: InstructorViewHolder, position: Int) {
    holder.instructorName.text = instructorList[position].iname
    holder.instructorEmail.text = instructorList[position].iemail
    holder.instructorId.text = instructorList[position].instructorid

        holder.updateImageButton.setOnClickListener(){
            val intent = Intent(context, Instructor_Delete_Update::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("iID", instructorList[position].instructorid)
            intent.putExtra("iName", instructorList[position].iname)
            intent.putExtra("iEmail", instructorList[position].iemail)
            context.startActivity(intent)
        }
        holder.deleteInstructor.setOnClickListener(){
            instructorList[position].instructorid?.let { it1 -> deleteInstructor(it1,position) }
        }
    }

    private fun deleteInstructor(id: String, position: Int) {

        lateinit var builder: androidx.appcompat.app.AlertDialog.Builder

        builder = androidx.appcompat.app.AlertDialog.Builder(context)

        builder.setTitle("Alert")
            .setMessage("Do you want to Delete?")
            .setCancelable(true)
            .setPositiveButton("Yes"){ _: DialogInterface, _: Int ->

                db.collection("instructors")
                    .document(id)
                    .delete()
                    .addOnCompleteListener {
                        instructorList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, instructorList.size)
                        Toast.makeText(context, "Event has been deleted!", Toast.LENGTH_SHORT).show()
                    }

            }
            .setNegativeButton("Cancel"){ dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .show()


    }

    private fun updateInstructor(instructor: Instructor) {

    }
}