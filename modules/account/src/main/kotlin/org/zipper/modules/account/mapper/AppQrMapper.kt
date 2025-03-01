package org.zipper.modules.account.mapper

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.zipper.modules.account.domain.entity.AppQrEntity
import org.zipper.modules.account.domain.vo.AppQrVo

@Mapper
interface AppQrMapper : BaseMapper<AppQrEntity> {
    fun selectAppAccountList(
        @Param("page") page: Page<AppQrEntity>,
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<AppQrEntity>
    ): List<AppQrVo>
}