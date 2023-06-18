package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class activity_sell_item : AppCompatActivity() {

    //Declare Variable
    private lateinit var imageView: ImageView
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_PICK = 2
    private val CAMERA_PERMISSION_REQUEST_CODE = 1001
    private val CAMERA_REQUEST_CODE = 1002
    private lateinit var selItemTitle:EditText
    private lateinit var selItemPrice:EditText
    private lateinit var selItemSize:EditText
    private lateinit var selItemDescription:EditText
    private lateinit var sellItem: Button
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private lateinit var pb: ProgressBar

    val sellItemValidation = Sell_Item_Validation()

    private lateinit var storageReference: FirebaseStorage
    private var uri: Uri? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_item)

        storageReference = FirebaseStorage.getInstance()

        selItemTitle = findViewById(R.id.selItemTitle)
        selItemPrice = findViewById(R.id.selItemPrice)
        selItemSize = findViewById(R.id.selItemSize)
        selItemDescription = findViewById(R.id.selItemDescription)

        pb = findViewById(R.id.progressBar8)

        auth = FirebaseAuth.getInstance()

        sellItem = findViewById(R.id.sellItem)

        imageView = findViewById(R.id.selItemImg)
        imageView.setOnClickListener { selectImage() }

        pb.visibility = View.INVISIBLE

        sellItem.setOnClickListener() {

            //Add Img Validation
            if (uri != null) {

                // Rest of the code that uses uri
                pb.visibility = View.VISIBLE

                val randomID = UUID.randomUUID().toString()
                val sellerID = auth.currentUser?.uid.toString()
                val eselItemTitle = selItemTitle.text.toString().trim()
                val eselItemPrice = selItemPrice.text.toString().trim()
                val eselItemSize = selItemSize.text.toString().trim()
                val eselItemDescription = selItemDescription.text.toString().trim()

                //sell item Validation
                if (sellItemValidation.sellItemValidationFields(
                        eselItemTitle,
                        eselItemPrice,
                        eselItemSize,
                        eselItemDescription
                    )
                ) {

                    uri?.let { it1 ->
                        storageReference.getReference("Images/$sellerID")
                            .child(System.currentTimeMillis().toString())
                            .putFile(it1)
                            .addOnSuccessListener { task ->
                                task.metadata!!.reference!!.downloadUrl
                                    .addOnSuccessListener { uri ->

                                        val sellerMap = hashMapOf(
                                            "sellerID" to sellerID,
                                            "randomID" to randomID,
                                            "itemTitle" to eselItemTitle,
                                            "ItemPrice" to eselItemPrice,
                                            "itemSize" to eselItemSize,
                                            "ItemDescription" to eselItemDescription,
                                            "url" to uri
                                        )

                                        db.collection("seller").document(randomID).set(sellerMap)
                                        db.collection("sellerItemsBySellerID").document(sellerID)
                                            .collection("singleSellerItems").document(randomID)
                                            .set(sellerMap)
                                            .addOnSuccessListener {
                                                pb.visibility = View.INVISIBLE
                                                val i =
                                                    Intent(this, Item_Details_Activity::class.java)
                                                startActivity(i)
                                                finish()
                                            }
                                            .addOnFailureListener {
                                                pb.visibility = View.INVISIBLE
                                                Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT)
                                                    .show()
                                            }
                                    }

                            }
                    }

                } else {
                    pb.visibility = View.INVISIBLE
                    Toast.makeText(this, "All Filed Are Required", Toast.LENGTH_SHORT).show()

                }

            }else{
                    pb.visibility = View.INVISIBLE
                    Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()

            }
        }



    }

    private fun selectImage() {

        val options = arrayOf<CharSequence>("Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Choose from Gallery" -> {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, REQUEST_IMAGE_PICK)
                }
                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(imageBitmap)
                }
                REQUEST_IMAGE_PICK -> {
                    val imageUri = data?.data
                    imageView.setImageURI(imageUri)
                    if (imageUri != null) {
                        uri = imageUri
                    }
                }
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, start the camera activity
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
                } else {
                    // Permission denied, show a message to the user
                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }




}
