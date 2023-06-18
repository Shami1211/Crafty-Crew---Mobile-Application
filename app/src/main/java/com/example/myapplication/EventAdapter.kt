package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private val eventList:ArrayList<Event>) :RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val eventID :TextView = itemView.findViewById(R.id.eventID)
        val eventTitle :TextView = itemView.findViewById(R.id.eventTitle)
        val eventPlace :TextView = itemView.findViewById(R.id.eventPlace)
        val eventDate :TextView = itemView.findViewById(R.id.eventDate)
        val eventTime :TextView = itemView.findViewById(R.id.eventTime)
        val eventRandomID: TextView = itemView.findViewById(R.id.eventRandomID)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val eventView = LayoutInflater.from(parent.context).inflate(R.layout.single_event_to_seller,parent, false)
        return EventViewHolder(eventView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.eventID.text = eventList[position].eid
        holder.eventTitle.text = eventList[position].title
        holder.eventPlace.text = eventList[position].place
        holder.eventDate.text = eventList[position].date
        holder.eventTime.text = eventList[position].time
        holder.eventRandomID.text = eventList[position].eventID

    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}