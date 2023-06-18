package com.example.myapplication

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class SellerAdapter (
    private val itemsList: ArrayList<Seller>,
    private val context: Context,
    private val db: FirebaseFirestore,
    private val userID: String
) :RecyclerView.Adapter<SellerAdapter.SellerViewHolder> () {

   class SellerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
       val sellerID: TextView = itemView.findViewById(R.id.sellerId)
       val itemTitle: TextView = itemView.findViewById(R.id.selItemTitle)
       val itemPrice: TextView = itemView.findViewById(R.id.price)
       val itemSize: TextView = itemView.findViewById(R.id.size)
       val itemDes: TextView = itemView.findViewById(R.id.desription)
       val randomID: TextView = itemView.findViewById(R.id.randomId)
       val image: ImageView = itemView.findViewById(R.id.imageView3)
       //button
       val updateBtn: Button = itemView.findViewById(R.id.button5)
       val deleteBtn: Button = itemView.findViewById(R.id.button2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellerViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.single_item_to_seller,parent,false)
        return SellerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
     return itemsList.size

    }

    override fun onBindViewHolder(holder: SellerViewHolder, position: Int) {
        holder.sellerID.text = itemsList[position].sellerID
        holder.itemTitle.text = itemsList[position].itemTitle
        holder.itemPrice.text = itemsList[position].ItemPrice
        holder.itemSize.text = itemsList[position].itemSize
        holder.itemDes.text = itemsList[position].ItemDescription
        holder.randomID.text = itemsList[position].randomID
        Glide.with(context).load(itemsList[position].url).into(holder.image)

        holder.updateBtn.setOnClickListener(){
            val intent = Intent(context, activity_Update_Item::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("image", itemsList[position].url)
            intent.putExtra("itemID", itemsList[position].sellerID)
            intent.putExtra("randomID", itemsList[position].randomID)
            intent.putExtra("itemPrice", itemsList[position].ItemPrice)
            intent.putExtra("itemTitle", itemsList[position].itemTitle)
            intent.putExtra("itemSize", itemsList[position].itemSize)
            intent.putExtra("itemDescription", itemsList[position].ItemDescription)
            context.startActivity(intent)
        }

        holder.deleteBtn.setOnClickListener(){
            itemsList[position].randomID?.let { it1 -> itemsList[position].sellerID?.let { it2 ->
                deleteSellItem(it1,position,
                    it2
                )
            } }
        }
    }

    //delete Sell item
    private fun deleteSellItem(randomID: String, position: Int, sellerID:String) {

        lateinit var builder: AlertDialog.Builder

        builder = AlertDialog.Builder(context)

        builder.setTitle("Alert")
            .setMessage("Do you want to Delete?")
            .setCancelable(true)
            .setPositiveButton("Yes"){ _: DialogInterface, _: Int ->

                db.collection("seller")
                    .document(randomID)
                    .delete()
                    .addOnCompleteListener {
                        itemsList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, itemsList.size)
                        Toast.makeText(context, "Item has been deleted!", Toast.LENGTH_SHORT).show()
                    }

                db.collection("sellerItemsBySellerID").document(sellerID).collection("singleSellerItems")
                    .document(randomID)
                    .delete()
            }
            .setNegativeButton("Cancel"){ dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .show()




    }

}