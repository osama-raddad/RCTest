package com.example.osama.myapplication

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.osama.myapplication.R.id.daysRecyclerView
import java.util.ArrayList

class DaysOverViewAdapter(private val dayOverViews: List<DayOverView>) : RecyclerView.Adapter<DaysOverViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysOverViewAdapter.ViewHolder = ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_day_overview, parent, false))

    override fun onBindViewHolder(viewHolder: DaysOverViewAdapter.ViewHolder, position: Int) {
        viewHolder.title.text = dayOverViews[position].dayTitle
        viewHolder.dayPartsRecyclerView.layoutManager = CustomLinearLayoutManager(viewHolder.title.context, LinearLayoutManager.HORIZONTAL, false)
        viewHolder.dayPartsRecyclerView.isNestedScrollingEnabled = true
        viewHolder.dayPartsRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        viewHolder.dayPartsRecyclerView.addItemDecoration(DividerItemDecoration(viewHolder.title.context, DividerItemDecoration.HORIZONTAL))
        viewHolder.dayPartsRecyclerView.hasFixedSize()

        viewHolder.dayPartsRecyclerView.adapter = DayOverViewAdapter(ArrayList())
        (viewHolder.dayPartsRecyclerView.adapter as DayOverViewAdapter).setDayOverViewItems(dayOverViews[position].dayParts)
    }

    override fun getItemCount(): Int = dayOverViews.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.dayTitle)
        var dayPartsRecyclerView: RecyclerView = itemView.findViewById(R.id.dayPartsRecyclerView)
    }
}
