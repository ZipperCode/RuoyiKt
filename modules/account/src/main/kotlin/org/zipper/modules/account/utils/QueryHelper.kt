package org.zipper.modules.account.utils

import org.zipper.common.core.modules.ISysUserApi

object QueryHelper {

    /**
     * 查询参数是否包含上传人，并查询上传人的id
     */
    fun ISysUserApi.findUploadUserIfCondition(params: Map<String, Any>): List<Long> {
        val uploader = params["uploader"]?.toString()
        // 通过上传人，查询对应的记录id
        var uploaderIds: List<Long> = emptyList()
        if (!uploader.isNullOrEmpty()) {
            val userList = getUserContainsName(uploader)
            if (userList.isNotEmpty()) {
                uploaderIds = userList.mapNotNull { it.userId }
            }
        }
        return uploaderIds
    }

}