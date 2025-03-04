package org.zipper.modules.account.service

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.zipper.common.core.domain.mixin.sys.SysUserMixin
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.MapStructExt.convert
import org.zipper.common.core.ext.MapStructExt.convertList
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
import org.zipper.modules.account.domain.entity.AppAccountRecordEntity
import org.zipper.modules.account.domain.param.AppAccountParam
import org.zipper.modules.account.domain.param.AppAccountRecordParam
import org.zipper.modules.account.domain.param.SearchAccountParam
import org.zipper.modules.account.domain.param.UploadAccountParam
import org.zipper.modules.account.domain.vo.*
import org.zipper.modules.account.mapper.AppAccountMapper
import org.zipper.modules.account.mapper.AppAccountRecordMapper
import org.zipper.modules.account.utils.QueryHelper.findUploadUserIfCondition
import org.zipper.modules.account.utils.isSalesman


@Service
class AppAccountServiceImpl(
    private val appAccountMapper: AppAccountMapper,
    private val recordMapper: AppAccountRecordMapper,
    private val sysUserApi: ISysUserApi
) : AppAccountService {
    override fun add(param: AppAccountParam): Boolean {
        val loginUser = LoginHelper.requireLoginUser()
        if (loginUser.isSalesman()) {
            throw ServiceException("存在业务员权限，无法操作")
        }
        if (param.status == null) {
            param.status = DataStatus.Normal.status
        }

        val startTime = TimeUtils.getBeijingTimeBeforeFiveMonth()
        val exists = appAccountMapper.exists(
            MybatisKt.ktQuery<AppAccountEntity>()
                .eq(AppAccountEntity::account, param.account)
                .gt(AppAccountEntity::createTime, startTime)
                .select(AppAccountEntity::id)
        )
        log.info("新增账号：${param.account}，类型：${param.classify}，状态：${param.status}，限制时间：${startTime}，exists：$exists")
        if (exists) {
            throw ServiceException("账号已存在，请勿重复添加, 限制时间: ${startTime.formatYmdHms()}")
        }
        return appAccountMapper.insert(param.convert()) > 0
    }

    override fun edit(param: AppAccountParam): Boolean {
        val data = appAccountMapper.selectOne(
            MybatisKt.ktQuery<AppAccountEntity>()
                .eq(AppAccountEntity::id, param.id)
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

        var result = appAccountMapper.updateById(param.convert()) > 0
        if (result && modifyStatus) {
            val record = recordMapper.selectOne(
                MybatisKt.ktQuery<AppAccountRecordEntity>()
                    .eq(AppAccountRecordEntity::accountId, param.id)
            )
            if (record != null) {
                record.used = param.status
                result = recordMapper.updateById(record) > 0
            }
        }

        return result
    }

    override fun delete(ids: Array<Long>) {
        appAccountMapper.deleteBatchIds(ids.toList())
    }

    override fun updateStatus(ids: List<Long>, status: Int): Int {
        DataStatus.valid(status)
        val row = appAccountMapper.update(
            MybatisKt.ktUpdate<AppAccountEntity>()
                .`in`(AppAccountEntity::id, ids)
                .set(AppAccountEntity::status, status)
        )
        if (row < 0) {
            throw ServiceException("更新失败，未匹配到记录")
        }
        return row
    }

    override fun unBind(id: Long): Boolean {
        val recordEntity = recordMapper.selectOne(
            MybatisKt.ktQuery<AppAccountRecordEntity>()
                .eq(AppAccountRecordEntity::accountId, id)
                .select(AppAccountRecordEntity::id)
        ) ?: throw ServiceException("解绑失败，找不到记录")
        return recordMapper.deleteById(recordEntity.id) > 0
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun batchUpload(param: UploadAccountParam): AccountUploadResultVo {
        val file = param.file!!
        val classify = param.classify!!.toInt()
        val lines = file.inputStream.bufferedReader().use { it.readLines() }
        if (lines.isEmpty()) {
            throw ServiceException("上传文件内容为空")
        }
        val trimLines = lines.mapNotNull { if (it.trim().isEmpty()) null else it }
        val startTime = TimeUtils.getBeijingTimeBeforeFiveMonth()
        val existsList = appAccountMapper.selectList(
            MybatisKt.ktQuery<AppAccountEntity>()
                .`in`(AppAccountEntity::account, trimLines)
                .gt(AppAccountEntity::createTime, startTime)
                .select(AppAccountEntity::account)
        ).mapNotNull { it.account }.toSet()
        val uploadLines = trimLines - existsList

        val entities = uploadLines.map {
            AppAccountEntity().apply {
                this.account = it
                this.classify = classify
            }
        }
        appAccountMapper.insertBatch(entities)
        return AccountUploadResultVo(
            successCount = uploadLines.size,
            failureCount = existsList.size
        )
    }

    override fun getInfo(id: Long): AppAccountVo? {
        return appAccountMapper.selectById(id).convertOrNull()
    }

    override fun exportList(param: AppAccountParam): List<AccountExportVo> {
        val query = buildQuery(param)
        return appAccountMapper.selectAccountList(query).convertList()
    }

    override fun pageList(param: AppAccountParam, pageQuery: PageQuery): TableDataInfo<AppAccountVo> {
        DataClassify.valid(param.classify)
        val query = buildQuery(param)

        val queryPage = pageQuery.build<AppAccountEntity>()
        val dataList = appAccountMapper.selectAppAccountList(queryPage, query)
        val pageResult = Page<AppAccountVo>(queryPage.current, queryPage.size, queryPage.total)
        pageResult.setRecords(dataList)
        val selectUserIds = mutableListOf<Long>()
        dataList.forEach {
            it.collectUserIds(selectUserIds)
        }
        val userMap = sysUserApi.getUserListNames(selectUserIds).associateBy { it.userId }
        dataList.forEach {
            it.fillUser(userMap)
        }

        return TableDataInfo.build(pageResult)
    }

    private fun buildQuery(param: AppAccountParam): Wrapper<AppAccountEntity> {
        val classify = param.classify!!
        val loginUser = LoginHelper.requireLoginUser()
        val params = param.params
        // 通过上传人，查询对应的记录id
        val uploaderIds = sysUserApi.findUploadUserIfCondition(params)
        val isSalesman = loginUser.rolePermission.any { it.endsWith(RoleCode.Salesman.create(classify)) }
        val isUploader = loginUser.rolePermission.any { it.endsWith(RoleCode.Uploader.create(classify)) }
        log.info(
            "数据查询，是否业务员：{}，是否上传人：{} userId = {} username = {}",
            isSalesman, isUploader, loginUser.userId, loginUser.username
        )
        if (isSalesman) {
            // 上传人只查询可用状态的数据
            param.status = DataStatus.Normal.status
        }

        return Wrappers.query<AppAccountEntity>()
            .eq("a.classify", param.classify)
            // 业务员只能查询与自己相关的数据
            .eq(isSalesman, "r.bind_user_id", loginUser.userId)
            // 业务员只能查询自己创建的数据
            .eq(isUploader, "a.create_by", loginUser.userId)
            .eq(param.status != null, "a.status", param.status)
            .like(!param.account.isNullOrEmpty(), "a.account", param.account)
            .`in`(uploaderIds.isNotEmpty(), "a.create_by", uploaderIds)
            .like(!param.remark.isNullOrEmpty(), "a.remark", param.remark)
            .between(
                params["beginTime"] != null && params["endTime"] != null,
                "a.create_time", params["beginTime"], params["endTime"]
            )
    }

    override fun searchTypeList(param: SearchAccountParam, pageQuery: PageQuery): TableDataInfo<AppAccountVo> {
        // 通过上传人，查询对应的记录id
        val params = if (param.uploader != null) mapOf("uploader" to param.uploader!!) else emptyMap()
        val uploaderIds = sysUserApi.findUploadUserIfCondition(params)
        val searchClassify = DataClassify.waTypes()
        val query = Wrappers.query<AppAccountEntity>()
            .`in`("a.classify", searchClassify)
            .like(!param.account.isNullOrEmpty(), "a.account", param.account)
            .`in`(uploaderIds.isNotEmpty(), "a.create_by", uploaderIds)
            .between(
                params["beginTime"] != null && params["endTime"] != null,
                "a.create_time", params["beginTime"], params["endTime"]
            )
        val queryPage = pageQuery.build<AppAccountEntity>()
        val dataList = appAccountMapper.selectAppAccountList(queryPage, query)
        val pageResult = Page<AppAccountVo>(queryPage.current, queryPage.size, queryPage.total)

        val selectUserIds = mutableListOf<Long>()
        dataList.forEach {
            it.collectUserIds(selectUserIds)
        }
        val userMap = sysUserApi.getUserListNames(selectUserIds).associateBy { it.userId }
        dataList.forEach {
            it.fillUser(userMap)
        }
        pageResult.setRecords(dataList)
        return TableDataInfo.build(pageResult)
    }

    override fun recordPageList(param: AppAccountRecordParam, pageQuery: PageQuery): TableDataInfo<AppAccountRecordVo> {
        DataClassify.valid(param.classify)
        val loginUser = LoginHelper.requireLoginUser()
        val isSalesman = loginUser.rolePermission.any { it.endsWith(RoleCode.Salesman.create(param.classify!!)) }
        val query = Wrappers.query<AppAccountRecordEntity>()
            .eq("r.classify", param.classify)
            // 业务员只能查询分配自己的
            .eq(isSalesman, "r.bind_user_id", loginUser.userId)
            .eq(param.used != null, "r.used", param.used)
            .like(!param.account.isNullOrEmpty(), "a.account", param.account)
            .like(!param.createUser.isNullOrEmpty(), "u.user_name", param.createUser)
            .gt(param.params["beginTime"] != null, "r.create_time", param.params["beginTime"])
            .lt(param.params["endTime"] != null, "r.create_time", param.params["endTime"])

        val queryPage = pageQuery.build<AppAccountRecordEntity>()
        val dataList = recordMapper.selectPageList(queryPage, query)
        val pageResult = Page<AppAccountRecordVo>(queryPage.current, queryPage.size, queryPage.total)
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
    override fun dispatch(param: AppAccountParam): AppDispatchVo {
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
        val salesmanUserList = sysUserApi.selectUserByRoleCode(RoleCode.Salesman.create(classify)).filter { it.dispatch == "0" }
        if (salesmanUserList.isEmpty()) {
            throw ServiceException("没有可用的业务员账号")
        }
        val (start, end) = TimeUtils.getBeijingTimeRange()
        log.info("分配数据，可用业务员数量 = {}， 查询条件：classify = {}, start = {}, end = {}", salesmanUserList.size, classify, start, end)

        val dataList = appAccountMapper.selectList(
            MybatisKt.ktQuery<AppAccountEntity>()
                .eq(AppAccountEntity::classify, classify)
                .eq(AppAccountEntity::status, DataStatus.Normal.status)
                .between(param.dispatchAll != true, AppAccountEntity::createTime, start, end)
        )
        if (dataList.isEmpty()) {
            throw ServiceException("没有可用的数据")
        }
        val dataIds = dataList.mapNotNull { it.id }
        val recordAccountIds = recordMapper.selectList(
            MybatisKt.ktQuery<AppAccountRecordEntity>()
                .`in`(AppAccountRecordEntity::accountId, dataIds)
                .select(AppAccountRecordEntity::accountId)
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
            MybatisKt.ktQuery<AppAccountRecordEntity>()
                .between(AppAccountRecordEntity::createTime, start, end)
        ).associate { (it.bindUserId ?: 0L) to it.count }.toMutableMap()
        val maxOfUserCount = if (dayRecordMap.isNotEmpty()) dayRecordMap.maxOf { it.value } else 0
        val minOfUserCount = if (dayRecordMap.isNotEmpty()) dayRecordMap.minOf { it.value } else 0
        log.info("分配数据，查询到当天已经分配的记录数量：{} 记录数量最大为：{}, 最小为：{}", dayRecordMap, maxOfUserCount, minOfUserCount)
        val newDispatchRecordList = mutableListOf<AppAccountRecordEntity>()
        val dispatchFunc: (entity: AppAccountEntity, userId: Long) -> Unit = { entity, userId ->
            newDispatchRecordList.add(
                AppAccountRecordEntity().apply {
                    this.accountId = entity.id
                    this.bindUserId = userId
                    this.classify = classify
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
        if (newDispatchRecordList.isEmpty()) {
            throw ServiceException("分配数据失败, 匹配不到分配数据")
        }
        recordMapper.insertBatch(newDispatchRecordList)
        return AppDispatchVo(
            recordCount = newDispatchRecordList.size,
            userCount = salesmanUserList.size
        )
    }

    private fun dispatchUserAverage(
        dataList: MutableList<AppAccountEntity>,
        salesmanUserList: List<SysUserMixin>,
        div: Int,
        mod: Int,
        block: (entity: AppAccountEntity, userId: Long) -> Unit
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
        dataList: MutableList<AppAccountEntity>,
        dayRecordMap: MutableMap<Long, Int>,
        maxOfUserCount: Int, minOfUserCount: Int,
        block: (entity: AppAccountEntity, userId: Long) -> Unit
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