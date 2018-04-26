package com.jthou.skin;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;

import com.jthou.skin.attr.SkinAttr;
import com.jthou.skin.attr.SkinView;
import com.jthou.skin.callback.SkinListener;
import com.jthou.skin.utils.L;
import com.jthou.skin.utils.SPUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class SkinActivity extends AppCompatActivity implements LayoutInflater.Factory2, SkinListener {

    private static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};

    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    private static final Map<String, Constructor<? extends View>> sConstructorMap
            = new ArrayMap<>();

    private final Object[] mConstructorArgs = new Object[2];

    private static final Class<?>[] sCreateViewSignature = new Class[]{View.class, String.class, Context.class, AttributeSet.class};

    private final Object[] mCreateViewArgs = new Object[4];

    private Method mCreateViewMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final LayoutInflater inflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(inflater, this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        List<SkinAttr> skinAttrsList;
        SkinView skinView;
        try {

            // AppCompatDelegateImplV9做的工作
            AppCompatDelegate delegate = getDelegate();
            if (mCreateViewMethod == null) {
                mCreateViewMethod = delegate.getClass().getMethod("createView", sCreateViewSignature);
            }

            mCreateViewArgs[0] = parent;
            mCreateViewArgs[1] = name;
            mCreateViewArgs[2] = context;
            mCreateViewArgs[3] = attrs;
            view = (View) mCreateViewMethod.invoke(delegate, mCreateViewArgs);

            if (view == null) {
                view = createViewFromTag(context, name, attrs);
            }

            if (view == null) return null;

            skinAttrsList = SkinSupport.getAttrs(context, attrs);
            if (!skinAttrsList.isEmpty()) {
                skinView = new SkinView(view, skinAttrsList);
                SkinManager.with(this).addSkinView(this, skinView);
                if (SPUtils.getBoolean(context, Constant.KEY_APPLY_PLUGIN))
                    skinView.apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mCreateViewArgs[0] = null;
            mCreateViewArgs[1] = null;
            mCreateViewArgs[2] = null;
            mCreateViewArgs[3] = null;
        }

        return view;
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;

            if (-1 == name.indexOf('.')) {
                for (int i = 0; i < sClassPrefixList.length; i++) {
                    final View view = createView(context, name, sClassPrefixList[i]);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }

    private View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        }
    }

    @Override
    public void success() {
        L.e("换肤成功");
    }

    @Override
    public void failure() {
        L.e("换肤失败");
    }

    @Override
    protected void onDestroy() {
        SkinManager.with(this).removeSkinView(this);
        super.onDestroy();
    }

}
