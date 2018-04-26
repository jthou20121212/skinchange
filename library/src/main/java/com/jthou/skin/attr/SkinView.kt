package com.jthou.skin.attr

import android.view.View

class SkinView(private val mView: View, private val mAttrs: List<SkinAttr>) {

    fun apply() {
        for (attr in mAttrs) {
            attr.apply(mView)
        }
    }

}
