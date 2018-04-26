package com.jthou.skin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.jthou.skin.attr.SkinView;
import com.jthou.skin.callback.GlobalSkinListener;
import com.jthou.skin.callback.SkinListener;
import com.jthou.skin.resource.CurrentResourceManager;
import com.jthou.skin.resource.PluginResourceManager;
import com.jthou.skin.resource.ResourceManager;
import com.jthou.skin.utils.L;
import com.jthou.skin.utils.SPUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SkinManager {

    final Context context;
    final String prefix;
    final String pluginPackagePath;
    final String pluginPackageName;
    final GlobalSkinListener listener;

    volatile boolean plugin;
    volatile boolean loggingEnabled;

    SkinManager(Context context, String prefix, String pluginPackagePath, String pluginPackageName,
                GlobalSkinListener listener, boolean loggingEnabled, boolean plugin) {
        this.context = context;
        this.prefix = prefix;
        this.pluginPackagePath = pluginPackagePath;
        this.pluginPackageName = pluginPackageName;
        this.listener = listener;
        this.plugin = plugin;
        this.loggingEnabled = loggingEnabled;
        setLoggingEnabled(loggingEnabled);
    }

    static volatile SkinManager singleton = null;

    public static SkinManager with(Context context) {
        if (singleton == null) {
            synchronized (SkinManager.class) {
                if (singleton == null) {
                    singleton = new Builder(context).build();
                }
            }
        }
        return singleton;
    }

    public void setSuffix(String suffix) {
        // if (TextUtils.isEmpty(suffix)) return;
        SPUtils.putString(context, Constant.KEY_SUFFIX, suffix);
        resourceManager.setSuffix(suffix);
    }

    public String getSuffix() {
        return SPUtils.getString(context, Constant.KEY_SUFFIX);
    }

    public void setPlugin(boolean plugin) {
        this.plugin = plugin;
        if(plugin)
            resourceManager = new PluginResourceManager(pluginResources, pluginPackageName);
        else
            resourceManager = new CurrentResourceManager(mContext.getResources(), mContext.getPackageName());
    }

    public void setLoggingEnabled(boolean enabled) {
        loggingEnabled = enabled;
        L.isDebug = enabled;
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public static void setSingletonInstance(SkinManager picasso) {
        synchronized (SkinManager.class) {
            if (singleton != null) {
                throw new IllegalStateException("Singleton instance already exists.");
            }
            singleton = picasso;
        }
    }

    public static class Builder {
        private final Context context;
        private String prefix;
        private String pluginPackagePath;
        private String pluginPackageName;
        private GlobalSkinListener listener;

        // 开启log
        private boolean loggingEnabled;

        // true  使用插件包加载
        // false 正常加载
        private boolean plugin;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        public Builder prefix(String prefix) {
            if (TextUtils.isEmpty(prefix)) {
                throw new IllegalArgumentException("prefix must not be null");
            }
            if (!TextUtils.isEmpty(this.prefix)) {
                throw new IllegalStateException("prefix already set.");
            }
            this.prefix = prefix;
            return this;
        }

        public Builder pluginPackagePath(String pluginPackagePath) {
            if (TextUtils.isEmpty(pluginPackagePath)) {
                throw new IllegalArgumentException("pluginPackagePath must not be null");
            }
            if (!TextUtils.isEmpty(this.pluginPackagePath)) {
                throw new IllegalStateException("pluginPackagePath already set.");
            }
            this.pluginPackagePath = pluginPackagePath;
            return this;
        }

        public Builder pluginPackageName(String pluginPackageName) {
            if (TextUtils.isEmpty(pluginPackageName)) {
                throw new IllegalArgumentException("pluginPackagePath must not be null");
            }
            if (!TextUtils.isEmpty(this.pluginPackageName)) {
                throw new IllegalStateException("pluginPackageName already set.");
            }
            this.pluginPackageName = pluginPackageName;
            return this;
        }

        public Builder listener(GlobalSkinListener listener) {
            if (listener == null) {
                throw new IllegalArgumentException("Listener must not be null.");
            }
            if (this.listener != null) {
                throw new IllegalStateException("Listener already set.");
            }
            this.listener = listener;
            return this;
        }

        public Builder loggingEnabled(boolean enabled) {
            this.loggingEnabled = enabled;
            return this;
        }

        public Builder plugin(boolean enabled) {
            this.plugin = enabled;
            return this;
        }

        public SkinManager build() {
            if (TextUtils.isEmpty(prefix))
                prefix = Constant.PREFIX;

            if (TextUtils.isEmpty(pluginPackagePath))
                pluginPackagePath = Constant.PLUGIN_PACKAGE_PATH;

            if (TextUtils.isEmpty(pluginPackageName))
                pluginPackageName = Constant.PLUGIN_PACKAGE_NAME;

            return new SkinManager(context, prefix, pluginPackagePath, pluginPackageName, listener, loggingEnabled, plugin);
        }

    }

    private Resources pluginResources;

    private ResourceManager resourceManager;

    private Map<SkinListener, List<SkinView>> mSkinViews;

    private Context mContext;

    // 需要读取文件权限 result = 0 表示失败
    public void prepare() {
        try {
            mContext = context.getApplicationContext();

            AssetManager assets = AssetManager.class.newInstance();
            Method method = AssetManager.class.getMethod("addAssetPath", String.class);
            int result = (int) method.invoke(assets, pluginPackagePath);
            L.e("result : " + result);

            Resources resource = context.getResources();
            pluginResources = new Resources(assets, resource.getDisplayMetrics(), resource.getConfiguration());

            if (plugin)
                resourceManager = new PluginResourceManager(pluginResources, pluginPackageName);
            else
                resourceManager = new CurrentResourceManager(resource, mContext.getPackageName());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public void addSkinView(SkinListener listener, SkinView skinView) {
        if (mSkinViews == null)
            mSkinViews = new HashMap<>();
        List<SkinView> skinViews = mSkinViews.get(listener);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
            mSkinViews.put(listener, skinViews);
        }
        skinViews.add(skinView);
    }

    public void removeSkinView(SkinListener listener) {
        if (mSkinViews != null)
            mSkinViews.remove(listener);
    }

    // 换肤
    public void skinChange() {
//        if(SPUtils.getBoolean(mContext, Constant.KEY_APPLY_PLUGIN)) {
//            L.e("已经换肤，不需要再次换肤，除非你撒");
//            return;
//        }
        if (mSkinViews == null) {
            if (listener != null)
                listener.failure("没有需要换肤的控件，可能是资源名称前缀不正确");
            return;
        }
        Set<Map.Entry<SkinListener, List<SkinView>>> entries = mSkinViews.entrySet();
        Iterator<Map.Entry<SkinListener, List<SkinView>>> iterator = entries.iterator();
        boolean result = true;
        while (iterator.hasNext()) {
            Map.Entry<SkinListener, List<SkinView>> next = iterator.next();
            SkinListener key = next.getKey();
            List<SkinView> value = next.getValue();
            result &= skinChange(key, value);
        }
        // 保存换肤状态，下次自动换肤
        if (result) {
            SPUtils.putBoolean(mContext, Constant.KEY_APPLY_PLUGIN, true);
            if (listener != null)
                listener.success();
        } else {
            if (listener != null)
                listener.failure("");
        }
    }

    private boolean skinChange(SkinListener listener, List<SkinView> skinViews) {
        try {
            for (SkinView view : skinViews)
                view.apply();
            listener.success();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            listener.failure();
        }
        return false;
    }

    // 恢复默认
    public void clean() {
        SPUtils.clear(mContext);
        skinChange();
    }

}
