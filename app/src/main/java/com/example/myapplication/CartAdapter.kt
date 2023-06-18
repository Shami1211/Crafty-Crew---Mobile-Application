package com.example.myapplication

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class CartAdapter(
    private val cartItemList: ArrayList<Cart>,
    private val context: Context,
    private val db: FirebaseFirestore,
    private val userID: String
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val cartItemID: TextView = itemView.findViewById(R.id.cartItemID)
        val cartItemTitle: TextView = itemView.findViewById(R.id.time)
        val cartItemSize: TextView = itemView.findViewById(R.id.textView13)
        val cartItemQuantity: TextView = itemView.findViewById(R.id.textView14)
        val cartItemPrice: TextView = itemView.findViewById(R.id.textView15)

        val updateCartItemBtn: Button = itemView.findViewById(R.id.button5)
        val deleteCartItem: Button = itemView.findViewById(R.id.button2)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.single_cart_item,parent,false)
        return CartAdapter.CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.cartItemID.text = cartItemList[position].cartItemID
        holder.cartItemTitle.text = cartItemList[position].cartItemTitle
        holder.cartItemPrice.text = cartItemList[position].cartItemPrice
        holder.cartItemSize.text = cartItemList[position].cartItemSize
        holder.cartItemQuantity.text = cartItemList[position].cartItemQuantity

        holder.updateCartItemBtn.setOnClickListener(){
            val intent = Intent(context, Cart_Item_Update::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("cartItemID", cartItemList[position].cartItemID)
            intent.putExtra("cartItemTitle", cartItemList[position].cartItemTitle)
            intent.putExtra("cartItemPrice", cartItemList[position].cartItemPrice)
            intent.putExtra("cartItemSize", cartItemList[position].cartItemSize)
            intent.putExtra("cartItemQuantity", cartItemList[position].cartItemQuantity)
            context.startActivity(intent)
        }

        holder.deleteCartItem.setOnClickListener(){

            lateinit var builder: AlertDialog.Builder

            builder = AlertDialog.Builder(context)

            builder.setTitle("Alert")
                .setMessage("Do you want to Delete?")
                .setCancelable(true)
                .setPositiveButton("Yes"){ _: DialogInterface, _: Int ->

                    cartItemList[position].cartItemID?.let { it1 -> deleteCartItem(userID,position, it1) }

                }
                .setNegativeButton("Cancel"){ dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                }
                .show()

        }
    }

    private fun deleteCartItem(id: String, position: Int, randomID: String) {

        db.collection("cart").document(id).collection("singleUser")
            .document(randomID)
            .delete()
            .addOnCompleteListener {
                cartItemList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, cartItemList.size)
                Toast.makeText(context, "Item has been deleted!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(context, "Item Delete Failed!", Toast.LENGTH_SHORT).show()

            }
    }
    override fun getItemCount(): Int {
        return cartItemList.size
    }

}