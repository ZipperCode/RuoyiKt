package org.zipper.modules.account.utils

import org.zipper.common.core.domain.model.LoginUser
import org.zipper.modules.account.constant.RoleCode

fun LoginUser.isSalesman(): Boolean {
    return this.rolePermission.any { it.startsWith(RoleCode.Salesman.code) }
}

fun LoginUser.isUploader(): Boolean {
    return this.rolePermission.any { it.startsWith(RoleCode.Uploader.code) }
}

fun LoginUser.isAccountAdmin(): Boolean {
    return this.rolePermission.any { it.startsWith(RoleCode.Admin.code) }
}