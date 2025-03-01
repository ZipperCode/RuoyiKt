package org.zipper.modules.account.service

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.zipper.common.core.domain.mixin.sys.SysUserMixin
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.MapStructExt.convert
import org.zipper.common.core.ext.MapStructExt.convertOrNull
import org.zipper.common.core.ext.log
import org.zipper.common.core.modules.ISysUserApi
import org.zipper.common.core.utils.TimeUtils
import org.zipper.common.core.utils.TimeUtils.formatYmdHms
import org.zipper.framework.mybatis.core.MybatisKt
import org.zipper.framework.mybatis.core.insertBatch
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.utils.LoginHelper
import org.zipper.modules.account.constant.DataClassify
import org.zipper.modules.account.constant.DataStatus
import org.zipper.modules.account.constant.RoleCode
import org.zipper.modules.account.domain.entity.AppAccountEntity
import org.zipper.modules.account.domain.entity.AppQrEntity
import org.zipper.modules.account.domain.entity.AppQrRecordEntity
import org.zipper.modules.account.domain.param.AppAccountRecordParam
import org.zipper.modules.account.domain.param.AppQrParam
import org.zipper.modules.account.domain.param.UploadAccountParam
import org.zipper.modules.account.domain.vo.AccountUploadResultVo
import org.zipper.modules.account.domain.vo.AppDispatchVo
import org.zipper.modules.account.domain.vo.AppQrRecordVo
import org.zipper.modules.account.domain.vo.AppQrVo
import org.zipper.modules.account.mapper.AppQrMapper
import org.zipper.modules.account.mapper.AppQrRecordMapper
import org.zipper.modules.account.utils.isSalesman

@Service
class AppQrServiceImpl(
    private val appQrMapper: AppQrMapper,
    private val recordMapper: AppQrRecordMapper,
    private val sysUserApi: ISysUserApi
) : AppQrService {
    override fun add(param: AppQrParam): Boolean {
        val loginUser = LoginHelper.requireLoginUser()
        if (loginUser.isSalesman()) {
            throw ServiceException("存在业务员权限，无法操作")
        }
        if (param.status == null) {
            param.status = DataStatus.Normal.status
        }

        val startTime = TimeUtils.getBeijingTimeBeforeFiveMonth()
        val exists = appQrMapper.exists(
            MybatisKt.ktQuery<AppQrEntity>()
                .eq(AppQrEntity::qrContent, param.qrContent)
                .gt(AppQrEntity::createTime, startTime)
                .select(AppQrEntity::id)
        )
        log.info("新增链接：${param.qrContent}，类型：${param.classify}，状态：${param.status}，限制时间：${startTime}，exists：$exists")
        if (exists) {
            throw ServiceException("账号已存在，请勿重复添加, 限制时间: ${startTime.formatYmdHms()}")
        }
        return appQrMapper.insert(param.convert()) > 0
    }

    override fun edit(param: AppQrParam): Boolean {
        val data = appQrMapper.selectOne(
            MybatisKt.ktQuery<AppQrEntity>()
                .eq(AppQrEntity::id, param.id)
        )
        if (data == null) {
            throw ServiceException("未匹配到记录")
        }
        val loginUser = LoginHelper.requireLoginUser()
        var modifyStatus = false
        if (param.status != null) {
            DataStatus.valid(param.status!!)
            modifyStatus = data.status != param.status
            log.info("修改账号状态：${data.status} -> ${param.status}, 修改人：${loginUser.username}")
            if (modifyStatus && data.modifier == null) {
                param.modifier = loginUser.username
            } else if (modifyStatus && data.modifier != null) {
                throw ServiceException("使用状态只能修改一次，修改人为: ${data.modifier}")
            }
        }

        var result = appQrMapper.updateById(param.convert()) > 0
        if (result && modifyStatus) {
            val record = recordMapper.selectOne(
                MybatisKt.ktQuery<AppQrRecordEntity>()
                    .eq(AppQrRecordEntity::accountId, param.id)
            )
            if (record != null) {
                record.used = param.status
                result = recordMapper.updateById(record) > 0
            }
        }

        return result
    }

    override fun delete(ids: Array<Long>) {
        appQrMapper.deleteBatchIds(ids.toList())
    }

    override fun updateStatus(id: Long, status: Int): Int {
        val row = appQrMapper.update(
            MybatisKt.ktUpdate<AppQrEntity>()
                .eq(AppQrEntity::id, id)
                .set(AppQrEntity::status, status)
        )
        if (row < 0) {
            throw ServiceException("更新失败，未匹配到记录")
        }
        return status
    }

    override fun unBind(id: Long): Boolean {
        val recordEntity = recordMapper.selectOne(
            MybatisKt.ktQuery<AppQrRecordEntity>()
                .eq(AppQrRecordEntity::accountId, id)
                .select(AppQrRecordEntity::id)
        ) ?: throw ServiceException("解绑失败，找不到记录")
        return recordMapper.deleteById(recordEntity.id) > 0
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun batchUpload(param: UploadAccountParam): AccountUploadResultVo {
//        val file = param.file!!
//        val classify = param.classify!!.toInt()
//        val lines = file.inputStream.bufferedReader().use { it.readLines() }
//        if (lines.isEmpty()) {
//            throw ServiceException("上传文件内容为空")
//        }
//        val trimLines = lines.mapNotNull { if (it.trim().isEmpty()) null else it }
//        val startTime = TimeUtils.getBeijingTimeBeforeFiveMonth()
//        val existsList = appQrMapper.selectList(
//            MybatisKt.ktQuery<AppQrEntity>()
//                .`in`(AppQrEntity::account, trimLines)
//                .gt(AppQrEntity::createTime, startTime)
//                .select(AppQrEntity::account)
//        ).mapNotNull { it.account }.toSet()
//        val uploadLines = trimLines - existsList
//
//        val entities = uploadLines.map {
//            AppQrEntity().apply {
//                this.account = it
//                this.classify = classify
//            }
//        }
//        appQrMapper.insertBatch(entities)
        return AccountUploadResultVo(
            successCount = 0,
            failureCount = 0
        )
    }

    override fun getInfo(id: Long): AppQrVo? {
        return appQrMapper.selectById(id).convertOrNull()
    }

    override fun pageList(param: AppQrParam, pageQuery: PageQuery): TableDataInfo<AppQrVo> {
        val params = param.params
        // 上传的用户名
        val uploader = params["uploader"]?.toString()
        // 通过上传人，查询对应的记录id
        var uploaderIds: List<Long> = emptyList()
        if (!uploader.isNullOrEmpty()) {
            val userList = sysUserApi.getUserContainsName(uploader)
            if (userList.isNotEmpty()) {
                uploaderIds = userList.mapNotNull { it.userId }
            }
        }

        val query = Wrappers.query<AppQrEntity>()
            .eq("a.classify", param.classify)
            .eq(param.status != null, "a.status", param.status)
            .`in`(uploaderIds.isNotEmpty(), "a.create_by", uploaderIds)
            .like(!param.remark.isNullOrEmpty(), "a.remark", param.remark)
            .between(
                params["beginTime"] != null && params["endTime"] != null,
                "a.create_time", params["beginTime"], params["endTime"]
            )
        val queryPage = pageQuery.build<AppQrEntity>()
        val dataList = appQrMapper.selectAppAccountList(queryPage, query)
        val pageResult = Page<AppQrVo>(queryPage.current, queryPage.size, queryPage.total)
        pageResult.setRecords(dataList)
        val selectUserIds = mutableListOf<Long>()
        dataList.forEach {
            if (it.createBy != null) {
                selectUserIds.add(it.createBy!!)
            }
            it.record?.run {
                if (createBy != null) {
                    selectUserIds.add(createBy!!)
                }
                if (bindUserId != null) {
                    selectUserIds.add(bindUserId!!)
                }
            }
        }
        val userMap = sysUserApi.getUserListNames(selectUserIds).associateBy { it.userId }
        dataList.forEach {
            it.createUser = userMap[it.createBy]?.userName
            it.record?.run {
                createUser = userMap[createBy]?.userName
                bindUser = userMap[bindUserId]?.userName
            }
        }

        return TableDataInfo.build(pageResult)
    }

    override fun recordPageList(param: AppAccountRecordParam, pageQuery: PageQuery): TableDataInfo<AppQrRecordVo> {
        DataClassify.valid(param.classify)
        val query = Wrappers.query<AppQrRecordEntity>()
            .eq("r.classify", param.classify)
            .eq(param.used != null, "r.used", param.used)
            .like(!param.account.isNullOrEmpty(), "a.account", param.account)
            .like(!param.createUser.isNullOrEmpty(), "u.user_name", param.createUser)
            .gt(param.params["beginTime"] != null, "r.create_time", param.params["beginTime"])
            .lt(param.params["endTime"] != null, "r.create_time", param.params["endTime"])

        val queryPage = pageQuery.build<AppQrRecordEntity>()
        val dataList = recordMapper.selectPageList(queryPage, query)
        val pageResult = Page<AppQrRecordVo>(queryPage.current, queryPage.size, queryPage.total)
        pageResult.setRecords(dataList)
        val selectUserIds = mutableListOf<Long>()
        dataList.forEach {
            if (it.bindUserId != null) {
                selectUserIds.add(it.bindUserId!!)
            }
        }
        val userMap = sysUserApi.getUserListNames(selectUserIds).associateBy { it.userId }
        dataList.forEach {
            it.createUser = userMap[it.createBy]?.userName
            it.bindUser = userMap[it.bindUserId]?.userName
        }
        return TableDataInfo.build(pageResult)
    }

    @Transactional(rollbackFor = [Exception::class], noRollbackFor = [ServiceException::class])
    override fun dispatch(param: AppQrParam): AppDispatchVo {
        // 查询所有业务员账户
        if (param.classify == null) {
            throw ServiceException("类型不能为空")
        }
        val classify = param.classify!!
        val currentUser = LoginHelper.getLoginUser() ?: throw ServiceException("用户未登录")
        if (currentUser.rolePermission.any { it.startsWith(RoleCode.Salesman.code) }) {
            log.info(
                "业务员无法进行数据分配, classify = {}, user = {}, rolePermission = {}",
                classify,
                currentUser.userId,
                currentUser.rolePermission
            )
            throw ServiceException("业务员无法进行数据分配")
        }
        // 查找所有未使用的记录
        val salesmanUserList = sysUserApi.selectUserByRoleCode(RoleCode.Salesman.create(classify))
        if (salesmanUserList.isEmpty()) {
            throw ServiceException("没有可用的业务员账号")
        }
        val (start, end) = TimeUtils.getBeijingTimeRange()
        log.info("分配数据，可用业务员数量 = {}， 查询条件：classify = {}, start = {}, end = {}", salesmanUserList.size, classify, start, end)

        val dataList = appQrMapper.selectList(
            MybatisKt.ktQuery<AppQrEntity>()
                .eq(AppQrEntity::classify, classify)
                .eq(AppQrEntity::status, DataStatus.Normal.status)
                .between(param.dispatchAll != true, AppQrEntity::createTime, start, end)
        )
        if (dataList.isEmpty()) {
            throw ServiceException("没有可用的数据")
        }
        val dataIds = dataList.mapNotNull { it.id }
        val recordAccountIds = recordMapper.selectList(
            MybatisKt.ktQuery<AppQrRecordEntity>()
                .`in`(AppQrRecordEntity::accountId, dataIds)
                .select(AppQrRecordEntity::accountId)
        ).mapNotNull { it.accountId }
        log.info("分配数据，查询到未使用数据，但存在记录的数据：{}", recordAccountIds)
        val newDataList = dataList.toMutableList().apply {
            removeIf {
                it.id in recordAccountIds
            }
        }
        if (newDataList.isEmpty()) {
            throw ServiceException("没有可用的数据")
        }

        val userCount = salesmanUserList.size
        var accountCount = newDataList.size
        var div = accountCount / userCount
        var mod = accountCount % userCount
        log.info("分配数据，分配用户数 = {}, 分配数量= {}, 分配轮数 = {}， 分配余数 = {}", userCount, accountCount, div, mod)

        // 查询当天已经分配的记录数量
        val dayRecordMap = recordMapper.selectUserCount(
            MybatisKt.ktQuery<AppQrRecordEntity>()
                .between(AppQrRecordEntity::createTime, start, end)
        ).associate { (it.bindUserId ?: 0L) to it.count }.toMutableMap()
        val maxOfUserCount = dayRecordMap.maxOf { it.value }
        val minOfUserCount = dayRecordMap.minOf { it.value }
        log.info("分配数据，查询到当天已经分配的记录数量：{} 记录数量最大为：{}, 最小为：{}", dayRecordMap, maxOfUserCount, minOfUserCount)
        val newDispatchRecordList = mutableListOf<AppQrRecordEntity>()
        val dispatchFunc: (entity: AppQrEntity, userId: Long) -> Unit = { entity, userId ->
            newDispatchRecordList.add(
                AppQrRecordEntity().apply {
                    this.accountId = entity.id
                    this.bindUserId = userId
                    this.used = DataStatus.Normal.status
                }
            )
        }

        dispatchUserFill(newDataList, dayRecordMap, maxOfUserCount, minOfUserCount, dispatchFunc)
        accountCount = newDataList.size
        div = accountCount / userCount
        mod = accountCount % userCount
        log.info("分配数据，填充已有用户完成，开始进行平分处理，剩余分配数量= {}, 分配轮数 = {}， 分配余数 = {}", accountCount, div, mod)
        if (accountCount > 0) {
            // 填充完还有剩余
            dispatchUserAverage(newDataList, salesmanUserList, div, mod, dispatchFunc)
        }
        log.info("分配数据，分配完成，分配记录：{}", newDispatchRecordList)
        if (newDataList.isEmpty()) {
            throw ServiceException("分配数据失败, 没有")
        }
        recordMapper.insertBatch(newDispatchRecordList)
        return AppDispatchVo(
            recordCount = newDispatchRecordList.size,
            userCount = salesmanUserList.size
        )
    }

    private fun dispatchUserAverage(
        dataList: MutableList<AppQrEntity>,
        salesmanUserList: List<SysUserMixin>,
        div: Int,
        mod: Int,
        block: (entity: AppQrEntity, userId: Long) -> Unit
    ) {
        // 平分
        for (i in 0 until div) {
            for (sysUserMixin in salesmanUserList) {
                val userId = sysUserMixin.userId ?: continue
                val entity = dataList.removeLastOrNull() ?: return
                block(entity, userId)
            }
        }
        // 余数
        for (i in 0 until mod) {
            val userId = salesmanUserList[i].userId ?: continue
            val entity = dataList.removeLastOrNull() ?: return
            block(entity, userId)
        }
    }

    /**
     * 填充已有的用户数
     */
    private fun dispatchUserFill(
        dataList: MutableList<AppQrEntity>,
        dayRecordMap: MutableMap<Long, Int>,
        maxOfUserCount: Int, minOfUserCount: Int,
        block: (entity: AppQrEntity, userId: Long) -> Unit
    ) {
        for (i in 0 until maxOfUserCount - minOfUserCount) {
            dayRecordMap.forEach { (userId, count) ->
                if (count >= maxOfUserCount) {
                    return@forEach
                }
                val entity = dataList.removeLastOrNull() ?: return
                block(entity, userId)
                dayRecordMap[userId] = count + 1
            }
        }
    }
}