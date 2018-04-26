package com.jthou.skin.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class SPUtils {

    public static final String SHARED_PREFERENCE_NAME = "sp_skin"; // SharedPreference操作的文件

    public static void saveInt(Context context, String key, int value) {
        if (context == null) return;
        Editor editor = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static int getInt(Context context, String key) {
        if (context == null) return 0;
        SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        int value = shared.getInt(key, 0);
        return value;
    }

    public static void saveLong(Context context, String key, long value) {
        if (context == null) return;
        Editor editor = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static long getLong(Context context, String key) {
        if (context == null) return 0L;
        SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        long value = shared.getLong(key, 0L);
        return value;
    }

    public static void putBoolean(Context context, String key, boolean value) {
        if (context == null) return;
        Editor editor = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static boolean getBoolean(Context context, String key) {
        if (context == null) return false;
        SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        boolean value = shared.getBoolean(key, false);
        return value;
    }

    public static void putString(Context context, String key, String value) {
        if (context == null) return;
        Editor editor = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static String getString(Context context, String key) {
        if (context == null) return "";
        SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String value = shared.getString(key, "");
        return value;
    }

    public static void remove(Context context, String key) {
        if (context == null) return;
        SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = shared.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        public static void apply(Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void putStringSet(Context context, String key, Set<String> values) {
        if (context == null) return;
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putStringSet(key, values);
        SharedPreferencesCompat.apply(editor);
    }

    public static Set<String> getStringSet(Context context, String key) {
        if (context == null) return null;
        SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Set<String> value = shared.getStringSet(key, null);
        return value;
    }

    public static void clear(Context context) {
        if (context == null) return;
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

}
