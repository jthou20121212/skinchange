package com.jthou.skin.attr

import android.support.v4.view.ViewCompat
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jthou.skin.SkinManager
import com.jthou.skin.resource.ResourceManager
import com.jthou.skin.utils.L
import com.jthou.skin.utils.ViewHelper

enum class SkinAttrType(private val attrName: String) {

    TEXT_SIZE("textSize") {
        override fun apply(view: View, resName: String) {
            if (view is TextView) {
                val size = resourceManager.getTextSizeByResName(resName)
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
            }
        }
    },
    TEXT_COLOR("textColor") {
        override fun apply(view: View, resName: String) {
            if (view is TextView) {
                val colors = resourceManager.getColorStateListByResName(view.getContext(), resName)
                if (colors != null) view.setTextColor(colors)
            }
        }
    },
    SRC("src") {
        override fun apply(view: View, resName: String) {
            if (view is ImageView) {
                val drawable = resourceManager.getDrawableByResName(view.getContext(), resName)
                if (drawable != null) view.setImageDrawable(drawable)
            }
        }
    },
    TEXT("text") {
        override fun apply(view: View, resName: String) {
            if (view is TextView) {
                val text = resourceManager.getTextByResName(resName)
                if (!TextUtils.isEmpty(text))
                    view.text = text
            }
        }
    },
    DRAWABLE_LEFT("drawableLeft") {
        override fun apply(view: View, resName: String) {
            if (view is TextView) {
                val drawable = resourceManager.getDrawableByResName(view.getContext(), resName)
                if (drawable != null)
                    ViewHelper.setDrawLeftBitmap(drawable, view)
            }
        }
    },
    DRAWABLE_TOP("drawableTop") {
        override fun apply(view: View, resName: String) {
            if (view is TextView) {
                val drawable = resourceManager.getDrawableByResName(view.getContext(), resName)
                if (drawable != null)
                    ViewHelper.setDrawTopBitmap(drawable, view)
            }
        }
    },
    DRAWABLE_RIGHT("drawableRight") {
        override fun apply(view: View, resName: String) {
            if (view is TextView) {
                val drawable = resourceManager.getDrawableByResName(view.getContext(), resName)
                if (drawable != null)
                    ViewHelper.setDrawRightBitmap(drawable, view)
            }
        }
    },
    DRAWABLE_BOTTOM("drawableBottom") {
        override fun apply(view: View, resName: String) {
            if (view is TextView) {
                val drawable = resourceManager.getDrawableByResName(view.getContext(), resName)
                if (drawable != null)
                    ViewHelper.setDrawBottomBitmap(drawable, view)
            }
        }
    },
    BACKGROUND("background") {
        override fun apply(view: View, resName: String) {
            val background = resourceManager.getDrawableByResName(view.context, resName)
            if (background != null) ViewCompat.setBackground(view, background)
        }
    };

    val resourceManager: ResourceManager
        get() = SkinManager.with(null).resourceManager

    abstract fun apply(view: View, resName: String)

    companion object {

        fun attrNameOf(resName: String): SkinAttrType? {
            val values = values()
            return values.firstOrNull { TextUtils.equals(resName, it.attrName) }
        }
    }

}
