package com.example.osama.myapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


internal class DayOverViewAdapter internal constructor(private var dayParts: List<DayPart>?) : RecyclerView.Adapter<DayOverViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_day_parts, parent, false))
    }

    override fun getItemCount(): Int {
        return dayParts!!.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

    }

    internal fun setDayOverViewItems(dayOverViewItems: List<DayPart>) {
        dayParts = dayOverViewItems
        notifyDataSetChanged()
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
