package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BuyerEventsAdapter(
    private val buyerEventsList: ArrayList<EventToSeller>

) : RecyclerView.Adapter<BuyerEventsAdapter.BuyerEventsViewHolder>() {

    class BuyerEventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val eID: TextView = itemView.findViewById(R.id.eventID2)
        val eTitle: TextView = itemView.findViewById(R.id.eventTitle2)
        val ePlace: TextView = itemView.findViewById(R.id.eventPlace2)
        val eDate: TextView = itemView.findViewById(R.id.eventDate2)
        val eTime: TextView = itemView.findViewById(R.id.eventTime2)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyerEventsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.single_event_to_seller,parent,false)
        return BuyerEventsAdapter.BuyerEventsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BuyerEventsViewHolder, position: Int) {

        holder.eID.text = buyerEventsList[position].eid
        holder.eTitle.text = buyerEventsList[position].title
        holder.ePlace.text = buyerEventsList[position].place
        holder.eDate.text = buyerEventsList[position].date
        holder.eTime.text = buyerEventsList[position].time
    }

    override fun getItemCount(): Int {
        return buyerEventsList.size
    }
}