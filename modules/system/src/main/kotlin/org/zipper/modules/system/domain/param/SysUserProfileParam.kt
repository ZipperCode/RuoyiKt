package org.zipper.modules.system.domain.param

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import org.zipper.common.sensitive.annotation.Sensitive
import org.zipper.common.sensitive.core.SensitiveStrategy
import org.zipper.framework.core.xss.Xss
import org.zipper.framework.mybatis.core.domain.BaseMixinVo

/**
 * 个人信息业务处理
 *
 * @author Michelle.Chung
 */
class SysUserProfileParam : BaseMixinVo() {
    /**
     * 用户ID
     */
    var userId: Long? = null

    /**
     * 用户昵称
     */
    @field:Xss(message = "用户昵称不能包含脚本字符")
    @field:Size(min = 0, max = 30, message = "用户昵称长度不能超过{max}个字符")
    var nickName: String? = null

    /**
     * 用户邮箱
     */
    @field:Sensitive(strategy = SensitiveStrategy.EMAIL)
    @field:Email(message = "邮箱格式不正确")
    @field:Size(min = 0, max = 50, message = "邮箱长度不能超过{max}个字符")
    var email: String? = null

    /**
     * 手机号码
     */
    @field:Sensitive(strategy = SensitiveStrategy.PHONE)
    var phonenumber: String? = null

    /**
     * 用户性别（0男 1女 2未知）
     */
    var sex: String? = null
}
