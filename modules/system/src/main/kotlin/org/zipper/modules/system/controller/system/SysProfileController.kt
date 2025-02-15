package org.zipper.modules.system.controller.system

import cn.dev33.satoken.secure.BCrypt
import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.io.FileUtil
import org.apache.commons.lang3.StringUtils
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.zipper.common.core.constant.FileExtConstants
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.framework.store.IStorageService
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.framework.security.utils.LoginHelper
import org.zipper.modules.system.domain.param.SysUserParam
import org.zipper.modules.system.domain.param.SysUserPasswordParam
import org.zipper.modules.system.domain.param.SysUserProfileParam
import org.zipper.modules.system.domain.vo.AvatarVo
import org.zipper.modules.system.domain.vo.ProfileVo
import org.zipper.modules.system.service.user.ISysUserService
import org.zipper.optional.encrypt.annotation.ApiEncrypt

/**
 * 个人信息 业务处理
 *
 * @author Lion Li
 */
@Validated
@RestController
@RequestMapping("/system/user/profile")
class SysProfileController(
    private val userService: ISysUserService,
    private val storageService: IStorageService,
) {
    /**
     * 个人信息
     */
    @GetMapping
    @ResultBody
    fun profile(): ProfileVo {
        val user = userService.selectUserById(LoginHelper.getUserId())
        val profileVo = ProfileVo()
        profileVo.user = user
        profileVo.roleGroup = userService.selectUserRoleGroup(user!!.userName)
        profileVo.postGroup = userService.selectUserPostGroup(user.userName)
        return profileVo
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResultBody
    fun updateProfile(@RequestBody profile: SysUserProfileParam?) {
        val user = BeanUtil.toBean(profile, SysUserParam::class.java)
        val username = LoginHelper.getUserName()
        if (StringUtils.isNotEmpty(user.phonenumber) && !userService.checkPhoneUnique(user)) {
            throw ServiceException("修改用户'$username'失败，手机号码已存在")
        }
        if (StringUtils.isNotEmpty(user.email) && !userService.checkEmailUnique(user)) {
            throw ServiceException("修改用户'$username'失败，邮箱账号已存在")
        }
        user.userId = LoginHelper.getUserId()
        if (userService.updateUserProfile(user) <= 0) {
            throw ServiceException("修改个人信息异常，请联系管理员")
        }
    }

    /**
     * 重置密码
     *
     * @param bo 新旧密码
     */
    @ApiEncrypt
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    @ResultBody
    fun updatePwd(@Validated @RequestBody bo: SysUserPasswordParam) {
        val user = userService.selectUserById(LoginHelper.getUserId())
        val password = user!!.password
        if (!BCrypt.checkpw(bo.oldPassword, password)) {
            throw ServiceException("修改密码失败，旧密码错误")
        }
        if (BCrypt.checkpw(bo.newPassword, password)) {
            throw ServiceException("新密码不能与旧密码相同")
        }

        if (userService.resetUserPwd(user.userId, BCrypt.hashpw(bo.newPassword)) <= 0) {
            throw ServiceException("修改密码异常，请联系管理员")
        }
    }

    /**
     * 头像上传
     *
     * @param avatarFile 用户头像
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping(value = ["/avatar"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResultBody
    fun avatar(@RequestPart("avatarfile") avatarFile: MultipartFile): AvatarVo {
        if (avatarFile.isEmpty) {
            throw ServiceException("上传图片不能为空")
        }
        val extension = FileUtil.extName(avatarFile.originalFilename)
        if (!StringUtils.equalsAnyIgnoreCase(extension, *FileExtConstants.IMAGE_EXTENSION)) {
            throw ServiceException("文件格式不正确，请上传" + FileExtConstants.IMAGE_EXTENSION.contentToString() + "格式")
        }
        val result = storageService.upload(avatarFile)
        if (userService.updateUserAvatar(LoginHelper.getUserId(), result.fileRecordId)) {
            return AvatarVo(result.url)
        }
        throw ServiceException("上传图片异常，请联系管理员")
    }
}
