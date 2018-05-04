package com.example.osama.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val days: MutableList<DayOverView> = ArrayList()
        val dayParts: MutableList<DayPart> = ArrayList()
        for (i in 1..30) dayParts.add(DayPart())
        for (i in 1..10) days.add(DayOverView("Day", dayParts))
        val adapter = DaysOverViewAdapter(days)

        val layout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        daysRecyclerView.layoutManager = layout
        daysRecyclerView.overScrollMode = View.OVER_SCROLL_IF_CONTENT_SCROLLS
        daysRecyclerView.isNestedScrollingEnabled = true
        daysRecyclerView.adapter = adapter
        val snapHelper = GravitySnapHelper(Gravity.END)
        snapHelper.attachToRecyclerView(daysRecyclerView)
    }
}
