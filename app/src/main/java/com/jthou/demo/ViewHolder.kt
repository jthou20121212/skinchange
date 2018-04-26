package com.jthou.demo

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jthou.demo.R

/**
 * Created by user on 2018/4/23.
 */
class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    val textView:TextView = itemView.findViewById(R.id.textView)
    val imageView:ImageView = itemView.findViewById(R.id.imageView)

}