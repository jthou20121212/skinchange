package com.jthou.skin

import android.os.Environment
import java.io.File

class Constant {

    companion object {
        @JvmField
        val KEY_APPLY_PLUGIN: String = "key_apply_plugin"
        @JvmField
        val PREFIX = "skin_"
        @JvmField
        val KEY_SUFFIX = "key_suffix"
        @JvmField
        val PLUGIN_PACKAGE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "plugin.apk"
        @JvmField
        val PLUGIN_PACKAGE_NAME = "com.plugin"
    }

}