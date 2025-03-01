package org.zipper.modules.account.mapper

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.zipper.modules.account.domain.entity.AppQrRecordEntity
import org.zipper.modules.account.domain.vo.AppQrRecordVo

@Mapper
interface AppQrRecordMapper : BaseMapper<AppQrRecordEntity> {

    fun selectPageList(
        @Param("page") page: Page<AppQrRecordEntity>,
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<AppQrRecordEntity>
    ): List<AppQrRecordVo>

    fun selectUserCount(
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<AppQrRecordEntity>
    ): List<AppQrRecordVo>
}