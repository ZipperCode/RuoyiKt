package org.zipper.modules.system.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.zipper.common.core.ext.MapStructExt.convertList
import org.zipper.framework.mybatis.core.MybatisKt
import org.zipper.modules.system.domain.entity.SysDictDataEntity
import org.zipper.modules.system.domain.vo.SysDictDataVo

/**
 * 字典表 数据层
 *
 * @author Lion Li
 */
interface SysDictDataMapper : BaseMapper<SysDictDataEntity>

fun SysDictDataMapper.selectDictDataByType(dictType: String?): List<SysDictDataVo> {
    return this.selectList(
        MybatisKt.ktQuery<SysDictDataEntity>()
            .eq(SysDictDataEntity::dictType, dictType)
            .orderByAsc(SysDictDataEntity::dictSort)
    ).convertList()
}
