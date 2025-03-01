package org.zipper.common.core.modules

import org.zipper.common.core.domain.mixin.sys.SysUserMixin

interface ISysUserApi {

    fun getUserById(userId: Long?): SysUserMixin?

    fun getUserListNames(userIds: List<Long>): List<SysUserMixin>

    fun getUserContainsName(name: String?): List<SysUserMixin>

    fun selectUserByRoleCode(code: String): List<SysUserMixin>
}