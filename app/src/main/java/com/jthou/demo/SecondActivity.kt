package com.jthou.demo

import android.os.Bundle
import android.view.View
import com.jthou.skin.SkinActivity
import com.jthou.skin.SkinManager
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : SkinActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val text: String = intent.getStringExtra("key_text")
        id_textView.text = text
        id_click.setOnClickListener(this)
        id_red.setOnClickListener(this)
        id_green.setOnClickListener(this)
        id_blue.setOnClickListener(this)
        id_default.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            id_click.id -> {
                SkinManager.with(this).setPlugin(true)
                SkinManager.with(this).skinChange()
            }
            id_red.id -> {
                SkinManager.with(this).setPlugin(false)
                SkinManager.with(this).suffix = "red"
                SkinManager.with(this).skinChange()
            }
            id_green.id -> {
                SkinManager.with(this).setPlugin(false)
                SkinManager.with(this).suffix = "green"
                SkinManager.with(this).skinChange()
            }
            id_blue.id -> {
                SkinManager.with(this).setPlugin(false)
                SkinManager.with(this).suffix = "blue"
                SkinManager.with(this).skinChange()
            }
            id_default.id -> {
                SkinManager.with(this).setPlugin(false)
                SkinManager.with(this).clean()
            }
        }
    }

}