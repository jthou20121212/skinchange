package com.jthou.demo

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jthou.skin.SkinActivity
import com.jthou.skin.SkinManager
import com.jthou.skin.callback.PermissionListener
import com.jthou.skin.utils.L
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : SkinActivity(), ItemClickSupport.OnItemClickListener, PermissionListener {

    private val mData: Array<Book?> = Book.books()

    private var mAdapter: Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestRuntimePermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), this)
        setContentView(R.layout.activity_main)
        mAdapter = Adapter(this, mData)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(this)
    }

    private var mListener: PermissionListener? = null

    private fun requestRuntimePermissions(permissions: Array<String>?, listener: PermissionListener?) {
        if (permissions == null || permissions.isEmpty()) return
        if (listener == null) throw NullPointerException("permissions is null")
        mListener = listener
        val permissionList = permissions.filterTo(LinkedList()) { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_DENIED }
        if (permissionList.isEmpty())
            mListener?.granted()
        else
            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), 0)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val permissionList = grantResults.indices
                .filter { grantResults[it] == PackageManager.PERMISSION_DENIED }
                .mapTo(LinkedList()) { permissions[it] }
        if (permissionList.isEmpty())
            mListener?.granted()
        else
            mListener?.denied(permissionList)
        // if(!ActivityCompat.shouldShowRequestPermissionRationale(this, permission));
    }

    override fun granted() {
        L.e("权限申请成功")
        SkinManager.with(this).prepare()
    }

    override fun denied(permissionList: List<String>) {
        L.e("权限申请失败")
    }

    override fun onItemClicked(recyclerView: RecyclerView?, position: Int, v: View?) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("key_text", mData[position]?.name)
        startActivity(intent)
    }

    override fun onDestroy() {
        ItemClickSupport.removeFrom(recyclerView)
        super.onDestroy()
    }

}
