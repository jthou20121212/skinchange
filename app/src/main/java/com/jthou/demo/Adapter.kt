package com.jthou.demo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jthou.demo.R

/**
 * Created by user on 2018/4/23.
 */
class Adapter(private val context: Context, private val mData: Array<Book?>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.textView?.text = mData[position]?.name
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.item, parent, false))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

}