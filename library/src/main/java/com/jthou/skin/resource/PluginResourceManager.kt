package com.jthou.skin.resource

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat

class PluginResourceManager(resources: Resources, pluginPackageName: String) : ResourceManager(resources, pluginPackageName) {

    override fun getDrawableByResName(context: Context, resName: String): Drawable? {
        return try {
            val id = mResources.getIdentifier(resName(resName), "drawable", mPluginPackageName)
            ContextCompat.getDrawable(context, id)
        } catch (e: NotFoundException) {
            e.printStackTrace()
            val color = getColorByResName(context, resName(resName))
            if (color == -1) null else ColorDrawable(color)
        }
    }

    @ColorInt
    override fun getColorByResName(context: Context, resName: String): Int {
        return try {
            val id = mResources.getIdentifier(resName(resName), "color", mPluginPackageName)
            ContextCompat.getColor(context, id)
        } catch (e: NotFoundException) {
            e.printStackTrace()
            -1
        }
    }

    override fun getColorStateListByResName(context: Context, resName: String): ColorStateList? {
        return try {
            val id = mResources.getIdentifier(resName(resName), "color", mPluginPackageName)
            ContextCompat.getColorStateList(context, id)
        } catch (e: NotFoundException) {
            e.printStackTrace()
            null
        }
    }

}
