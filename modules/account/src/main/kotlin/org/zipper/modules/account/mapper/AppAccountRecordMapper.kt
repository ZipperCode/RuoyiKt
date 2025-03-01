package org.zipper.modules.account.mapper

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.zipper.modules.account.domain.entity.AppAccountRecordEntity
import org.zipper.modules.account.domain.vo.AppAccountRecordVo

@Mapper
interface AppAccountRecordMapper : BaseMapper<AppAccountRecordEntity> {

    fun selectPageList(
        @Param("page") page: Page<AppAccountRecordEntity>,
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<AppAccountRecordEntity>
    ): List<AppAccountRecordVo>

    fun selectUserCount(
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<AppAccountRecordEntity>
    ): List<AppAccountRecordVo>
}