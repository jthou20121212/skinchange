package com.jthou.skin.callback

/**
 * Created by user on 2018/4/23.
 */
interface PermissionListener {

    fun granted()

    /**
     * 被拒绝的权限列表
     *
     * @param permissionList
     */
    fun denied(permissionList: List<String>)

}