package com.jthou.skin.attr

import android.view.View

class SkinAttr(private val mResName: String, private val mType: SkinAttrType) {

    fun apply(view: View) {
        mType.apply(view, mResName)
    }

}
