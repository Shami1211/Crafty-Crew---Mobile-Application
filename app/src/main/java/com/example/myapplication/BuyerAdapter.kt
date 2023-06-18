package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class BuyerAdapter(
    private val buyerItemList: ArrayList<Buyer>,
    private val context: Context,
    ) : RecyclerView.Adapter<BuyerAdapter.BuyerViewHolder>() {

    class BuyerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val sellerID: TextView = itemView.findViewById(R.id.buyItemId)
        val itemTitle: TextView = itemView.findViewById(R.id.buyItemTitle)
        val itemPrice: TextView = itemView.findViewById(R.id.buyItemPrice)
        val itemSize: TextView = itemView.findViewById(R.id.buyItemSize)
        val itemColor: TextView = itemView.findViewById(R.id.buyItemColor)
        val image: ImageView = itemView.findViewById(R.id.imageView6)

        val addToCart : TextView = itemView.findViewById(R.id.button5)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyerViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_singal_item_to_buyer,parent,false)
        return BuyerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return buyerItemList.size
    }

    override fun onBindViewHolder(holder: BuyerViewHolder, position: Int) {

        holder.sellerID.text = buyerItemList[position].sellerID
        holder.itemTitle.text = buyerItemList[position].itemTitle
        holder.itemPrice.text = buyerItemList[position].ItemPrice
        holder.itemSize.text = buyerItemList[position].itemSize
        holder.itemColor.text = buyerItemList[position].ItemDescription
        Glide.with(context).load(buyerItemList[position].url).into(holder.image)

        holder.addToCart.setOnClickListener(){
            val intent = Intent(context, Add_To_Cart::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("image", buyerItemList[position].url)
            intent.putExtra("itemID", buyerItemList[position].sellerID)
            intent.putExtra("iTitle", buyerItemList[position].itemTitle)
            intent.putExtra("iPrice", buyerItemList[position].ItemPrice)
            intent.putExtra("iSize", buyerItemList[position].itemSize)
            intent.putExtra("iDescription", buyerItemList[position].ItemDescription)
            context.startActivity(intent)
        }

    }
}