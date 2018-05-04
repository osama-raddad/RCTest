package com.example.osama.myapplication

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import java.util.*

class DayPartsView : RecyclerView {
    private var dayOverViewAdapter = DayOverViewAdapter(ArrayList())

    constructor(context: Context) : super(context) {
        initializeViews(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initializeViews(context)
    }

    constructor(context: Context,
                attrs: AttributeSet,
                defStyle: Int) : super(context, attrs, defStyle) {
        initializeViews(context)
    }

    private fun initializeViews(context: Context) {
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        this.isNestedScrollingEnabled = true
        this.overScrollMode = View.OVER_SCROLL_NEVER
        this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        this.hasFixedSize()

        this.adapter = dayOverViewAdapter
    }

    fun setDayOverViewItems(dayOverViewItems: List<DayPart>) {
        dayOverViewAdapter.setDayOverViewItems(dayOverViewItems)
    }

}
