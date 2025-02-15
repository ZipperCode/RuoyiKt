package org.zipper.modules.system.service.auth

import org.zipper.modules.system.domain.param.PasswordLoginParam
import org.zipper.modules.system.domain.vo.LoginVo

interface IAuthService {

    fun login(param: PasswordLoginParam): LoginVo

    fun logout()
}