package org.zipper.modules.system.domain.param

import jakarta.validation.constraints.NotBlank
import java.io.Serial
import java.io.Serializable

/**
 * 用户密码修改 dto
 */
class SysUserPasswordParam : Serializable {
    /**
     * 旧密码
     */
    @field:NotBlank(message = "旧密码不能为空")
    var oldPassword: String? = null

    /**
     * 新密码
     */
    @field:NotBlank(message = "新密码不能为空")
    var newPassword: String? = null

    companion object {
        @Serial
        var serialVersionUID = 1L
    }
}
