package com.jthou.demo

import android.app.Application
import android.os.Environment
import com.jthou.skin.SkinManager
import java.io.File

class SkinApplication: Application() {

    companion object {
        const val PLUGIN_NAME:String = "plugin-debug.zip"
        const val PLUGIN_PACKAGE_NAME:String = "com.jthou.plugin"
    }

    override fun onCreate() {
        super.onCreate()
        val pluginPath = Environment.getExternalStorageDirectory().toString() + File.separator + PLUGIN_NAME
        val builder = SkinManager.Builder(this)
        builder.pluginPackagePath(pluginPath).pluginPackageName(PLUGIN_PACKAGE_NAME).plugin(true)
        val skinManager = builder.build()
        SkinManager.setSingletonInstance(skinManager)
    }

}