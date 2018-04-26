package com.jthou.skin.callback

// 所有控件都换肤成功
interface GlobalSkinListener {

    fun success()

    fun failure(msg: String)

}