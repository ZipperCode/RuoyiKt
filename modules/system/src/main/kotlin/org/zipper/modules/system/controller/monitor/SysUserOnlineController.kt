package org.zipper.modules.system.controller.monitor

import cn.dev33.satoken.annotation.SaCheckPermission
import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.stp.StpUtil
import cn.hutool.core.bean.BeanUtil
import org.apache.commons.lang3.StringUtils
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.constant.CacheConstants
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.redis.utils.RedisUtils
import org.zipper.modules.system.domain.SysUserOnline

/**
 * 在线用户监控
 *
 * @author Lion Li
 */
@RestController
@RequestMapping("/monitor/online")
class SysUserOnlineController {
    /**
     * 获取在线用户监控列表
     *
     * @param ipaddr   IP地址
     * @param userName 用户名
     */
    @SaCheckPermission("monitor:online:list")
    @GetMapping("/list")
    fun list(ipaddr: String?, userName: String?): TableDataInfo<SysUserOnline> {
        // 获取所有未过期的 token
        val keys = StpUtil.searchTokenValue("", 0, -1, false)
        var userOnlineDTOList: MutableList<SysUserOnline> = ArrayList<SysUserOnline>()
        for (key in keys) {
            val token = StringUtils.substringAfterLast(key, ":")
            // 如果已经过期则跳过
            if (StpUtil.stpLogic.getTokenActiveTimeoutByToken(token) < -1) {
                continue
            }
            val bean = RedisUtils.getCacheObjectInline<SysUserOnline>(CacheConstants.ONLINE_TOKEN_KEY + token)
            if (bean != null) {
                userOnlineDTOList.add(bean)
            }
        }
        if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
            userOnlineDTOList = userOnlineDTOList.filter {
                StringUtils.equals(ipaddr, it.ipaddr) && StringUtils.equals(userName, it.userName)
            }.toMutableList()
        } else if (StringUtils.isNotEmpty(ipaddr)) {
            userOnlineDTOList = userOnlineDTOList.filter {
                StringUtils.equals(ipaddr, it.ipaddr)
            }.toMutableList()
        } else if (StringUtils.isNotEmpty(userName)) {
            userOnlineDTOList = userOnlineDTOList.filter {
                StringUtils.equals(userName, it.userName)
            }.toMutableList()
        }
        userOnlineDTOList.reverse()
        val userOnlineList = BeanUtil.copyToList(userOnlineDTOList, SysUserOnline::class.java)
        return TableDataInfo.build(userOnlineList)
    }

    /**
     * 强退用户
     *
     * @param tokenId token值
     */
    @SaCheckPermission("monitor:online:forceLogout")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    fun forceLogout(@PathVariable tokenId: String?) {
        try {
            StpUtil.kickoutByTokenValue(tokenId)
        } catch (ignored: NotLoginException) {
        }
    }
}
