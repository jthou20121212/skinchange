# 引入
```gradle
compile 'com.jthou:skinchange:1.0.0'
```
# 使用
```kotlin
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
```
使用Builder方式在Application中创建SkinManager对象，保证只创建一次
pluginPackagePath设置插件包位置，默认值Environment.getExternalStorageDirectory().toString() + File.separator + "plugin.apk"
pluginPackageName设置插件包包名，默认值com.plugin
plugin用于设置是否使用插件包，false表示不使用插件包，加载自己的资源
需要换肤的Activity需要继承SkinActivity，这里记录了所有需要换肤的控件
```kotlin
interface SkinListener {

    fun success()

    fun failure()

}
```
SkinActivity实现了SkinListener接口，用于接收换肤结果
```kotlin
SkinManager.with(this).prepare()
```
```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
调用prepare初始化，此方法需要读取文件权限
```kotlin
SkinManager.with(this).setPlugin(true)
SkinManager.with(this).skinChange()
```
使用skinChange换肤

# demo 
随便点击一项进入下一个页面，点击红、绿、蓝文字会变成相应的颜色。使用plugin打包修改apk文件名为plugin-debug.zip放入sd根目录点击换肤可以修改icon和文字颜色
