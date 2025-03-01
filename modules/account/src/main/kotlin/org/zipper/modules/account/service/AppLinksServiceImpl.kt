package org.zipper.modules.account.service

import org.springframework.stereotype.Service
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.MapStructExt.convert
import org.zipper.common.core.ext.MapStructExt.convertOrNull
import org.zipper.common.core.modules.ISysUserApi
import org.zipper.framework.mybatis.core.MybatisKt
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.mybatis.core.selectVoPage
import org.zipper.framework.security.utils.LoginHelper
import org.zipper.modules.account.constant.LinkDataClassify
import org.zipper.modules.account.domain.entity.AppLinksEntity
import org.zipper.modules.account.domain.param.AppLinksParam
import org.zipper.modules.account.domain.vo.AppLinksVo
import org.zipper.modules.account.mapper.AppLinksMapper

@Service
class AppLinksServiceImpl(
    private val userApi: ISysUserApi,
    private val appLinksMapper: AppLinksMapper
) : AppLinksService {
    override fun add(param: AppLinksParam): Boolean {
        LinkDataClassify.valid(param.classify)
        validateUploader()
        val count = appLinksMapper.selectCount(
            MybatisKt.ktQuery<AppLinksEntity>()
                .eq(AppLinksEntity::link, param.link ?: "")
        )
        if (count > 0) {
            throw ServiceException("链接已存在")
        }
        return appLinksMapper.insert(param.convert<AppLinksEntity>()) > 0
    }

    override fun edit(param: AppLinksParam): Boolean {
        LinkDataClassify.valid(param.classify)
        validateUploader()
        return appLinksMapper.updateById(param.convertOrNull()) > 0
    }

    override fun delete(ids: Array<Long>): Boolean {
        validateUploader()
        return appLinksMapper.deleteBatchIds(ids.toList()) > 0
    }

    override fun checkExists(id: Long): Boolean {
        return appLinksMapper.exists(
            MybatisKt.ktQuery<AppLinksEntity>().eq(AppLinksEntity::id, id)
        )
    }

    override fun getInfo(id: Long): AppLinksVo? {
        return appLinksMapper.selectById(id).convertOrNull()
    }

    override fun pageList(param: AppLinksParam, pageQuery: PageQuery): TableDataInfo<AppLinksVo> {
        val params = param.params
        val existsUploader = params["uploader"] != null
        var containUsers = emptyList<Long>()
        if (existsUploader) {
            containUsers = userApi.getUserContainsName(params["uploader"] as String?).mapNotNull { it.userId }
        }

        val query = MybatisKt.ktQuery<AppLinksEntity>()
            .eq(param.classify != null, AppLinksEntity::classify, param.classify)
            .like(!param.link.isNullOrEmpty(), AppLinksEntity::link, param.link)
            .like(!param.remark.isNullOrEmpty(), AppLinksEntity::remark, param.remark)
            .between(
                params["beginTime"] != null && params["endTime"] != null,
                AppLinksEntity::createTime, params["beginTime"], params["endTime"]
            )
            .eq(param.createBy != null, AppLinksEntity::createBy, param.createBy)
            .`in`(containUsers.isNotEmpty(), AppLinksEntity::createBy, containUsers)

        val page = appLinksMapper.selectVoPage<AppLinksEntity, AppLinksVo>(pageQuery.build(), query)
        val userIds = page.records.mapNotNull { it.createBy }
        if (userIds.isNotEmpty()) {
            val userMaps = userApi.getUserListNames(userIds).associateBy { it.userId }
            page.records.forEach {
                it.createUser = userMaps[it.createBy]?.userName
            }
        }

        return TableDataInfo.build(page)
    }

    private fun validateUploader() {
        val user = LoginHelper.getLoginUser() ?: throw ServiceException("用户获取失败")
        if (LoginHelper.isSuperAdmin(user.userId)) {
            return
        }
        if (user.rolePermission.any { it.contains("account_admin") }) {
            // 普通管理身份不不验证
            return
        }
        if (user.rolePermission.any { it.contains("_uploader") }) {
            throw ServiceException("粉端无权限操作")
        }
    }
}