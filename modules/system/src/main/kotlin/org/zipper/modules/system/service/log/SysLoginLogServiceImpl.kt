package org.zipper.modules.system.service.log

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import org.apache.commons.lang3.StringUtils
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.zipper.common.core.constant.Constants
import org.zipper.common.core.ext.MapStructExt.convert
import org.zipper.common.core.ext.MapStructExt.convertList
import org.zipper.common.core.ext.log
import org.zipper.common.core.framework.ip2area.Ip2AreaUtils
import org.zipper.framework.log.event.LoginLogEvent
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.mybatis.core.selectVoPage
import org.zipper.modules.system.domain.bo.SysLoginLogBo
import org.zipper.modules.system.domain.entity.SysLoginLogEntity
import org.zipper.modules.system.domain.vo.SysLoginLogVo
import org.zipper.modules.system.mapper.SysLoginLogMapper
import java.time.LocalDateTime
import java.util.*

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author Lion Li
 */
@Service
class SysLoginLogServiceImpl(
    private val baseMapper: SysLoginLogMapper,
) :
    ISysLoginLogService {
    /**
     * 记录登录信息
     *
     * @param loginLogEvent 登录事件
     */
    @Async
    @EventListener
    fun recordLoginLog(loginLogEvent: LoginLogEvent) {

        val address: String = Ip2AreaUtils.getAreaCity(loginLogEvent.ip)
        val s = StringBuilder()
        s.append(getBlock(loginLogEvent.ip))
        s.append(address)
        s.append(getBlock(loginLogEvent.username))
        s.append(getBlock(loginLogEvent.status))
        s.append(getBlock(loginLogEvent.message))
        // 打印信息到日志
        log.info(s.toString(), loginLogEvent.args)
        // 获取客户端操作系统
        val os = loginLogEvent.userAgent.os.name
        // 获取客户端浏览器
        val browser = loginLogEvent.userAgent.browser.name
        // 封装对象
        val loginLog = SysLoginLogBo()
        loginLog.userName = loginLogEvent.username
        loginLog.clientKey = loginLogEvent.clientKey
        loginLog.deviceType = loginLogEvent.deviceType
        loginLog.ipaddr = loginLogEvent.ip
        loginLog.loginLocation = address
        loginLog.browser = browser
        loginLog.os = os
        loginLog.msg = loginLogEvent.message
        // 日志状态
        if (StringUtils.equalsAny(loginLogEvent.status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            loginLog.status = Constants.SUCCESS
        } else if (Constants.LOGIN_FAIL == loginLogEvent.status) {
            loginLog.status = Constants.FAIL
        }
        // 插入数据
        insertLoginLog(loginLog)
    }

    private fun getBlock(msg: Any?): String {
        return "[$msg]"
    }

    override fun selectPageLoginLogList(loginLog: SysLoginLogBo, pageQuery: PageQuery): TableDataInfo<SysLoginLogVo> {
        val params: Map<String, Any?> = loginLog.params
        val lqw = KtQueryWrapper(SysLoginLogEntity::class.java)
            .like(StringUtils.isNotBlank(loginLog.ipaddr), SysLoginLogEntity::ipaddr, loginLog.ipaddr)
            .eq(StringUtils.isNotBlank(loginLog.status), SysLoginLogEntity::status, loginLog.status)
            .like(StringUtils.isNotBlank(loginLog.userName), SysLoginLogEntity::userName, loginLog.userName)
            .between(
                params["beginTime"] != null && params["endTime"] != null,
                SysLoginLogEntity::loginTime, params["beginTime"], params["endTime"]
            )
        if (StringUtils.isBlank(pageQuery.orderByColumn)) {
            pageQuery.orderByColumn = "info_id"
            pageQuery.isAsc = "desc"
        }

        val page = baseMapper.selectVoPage<SysLoginLogEntity, SysLoginLogVo>(pageQuery.build(), lqw)
        return TableDataInfo.build(page)
    }

    /**
     * 新增系统登录日志
     *
     * @param bo 访问日志对象
     */
    override fun insertLoginLog(bo: SysLoginLogBo) {
        val loginLog = bo.convert<SysLoginLogEntity>(SysLoginLogEntity())
        loginLog.loginTime = LocalDateTime.now()
        baseMapper.insert(loginLog)
    }

    /**
     * 查询系统登录日志集合
     *
     * @param loginLogBo 访问日志对象
     * @return 登录记录集合
     */
    override fun selectLoginLogList(loginLogBo: SysLoginLogBo): List<SysLoginLogVo> {
        val params: Map<String, Any?> = loginLogBo.params
        val lqw = KtQueryWrapper(SysLoginLogEntity::class.java)
            .like(StringUtils.isNotBlank(loginLogBo.ipaddr), SysLoginLogEntity::ipaddr, loginLogBo.ipaddr)
            .eq(StringUtils.isNotBlank(loginLogBo.status), SysLoginLogEntity::status, loginLogBo.status)
            .like(StringUtils.isNotBlank(loginLogBo.userName), SysLoginLogEntity::userName, loginLogBo.userName)
            .between(
                params["beginTime"] != null && params["endTime"] != null,
                SysLoginLogEntity::loginTime, params["beginTime"], params["endTime"]
            )
            .orderByDesc(SysLoginLogEntity::infoId)

        return baseMapper.selectList(lqw).convertList()
    }

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    override fun deleteLoginLogByIds(infoIds: Array<Long>): Int {
        return baseMapper.deleteBatchIds(listOf(*infoIds))
    }

    /**
     * 清空系统登录日志
     */
    override fun cleanLoginLog() {
        baseMapper.delete(KtQueryWrapper(SysLoginLogEntity::class.java))
    }
}
