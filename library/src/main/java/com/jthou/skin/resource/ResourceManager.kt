package com.jthou.skin.resource

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.text.TextUtils

import com.jthou.skin.SkinManager

/**
 * Created by user on 2018/4/26.
 */

abstract class ResourceManager(protected var mResources: Resources, protected var mPluginPackageName: String) {

    private var suffix: String? = null

    fun setSuffix(suffix: String) {
        this.suffix = suffix
    }

    init {
        this.suffix = SkinManager.with(null).suffix
    }

    fun getTextByResName(resName: String): String? {
        try {
            return mResources.getString(mResources.getIdentifier(resName(resName), "string", mPluginPackageName))
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    fun getTextByResName(resName: String, vararg formatArgs: Any): String? {
        try {
            return mResources.getString(mResources.getIdentifier(resName(resName), "string", mPluginPackageName), *formatArgs)
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    fun getTextSizeByResName(resName: String): Int {
        try {
            return mResources.getDimensionPixelSize(mResources.getIdentifier(resName(resName), "dimen", mPluginPackageName))
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }

        return 14
    }

    fun resName(resName: String): String {
        return if (TextUtils.isEmpty(suffix)) resName else resName + "_" + suffix
    }

    abstract fun getDrawableByResName(context: Context, resName: String): Drawable?

    @ColorInt
    abstract fun getColorByResName(context: Context, resName: String): Int

    abstract fun getColorStateListByResName(context: Context, resName: String): ColorStateList?

}
