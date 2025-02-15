package org.zipper.modules.system.domain.vo

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated
import com.alibaba.excel.annotation.ExcelProperty
import io.github.linpeilie.annotations.AutoMapper
import org.zipper.framework.excel.annotation.ExcelDictFormat
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.system.domain.entity.SysConfigEntity
import org.zipper.modules.system.domain.mixin.SysConfigMixin
import org.zipper.modules.system.excel.ExcelDictConvert
import java.time.LocalDateTime

/**
 * 参数配置视图对象 sys_config
 *
 * @author Michelle.Chung
 */
@ExcelIgnoreUnannotated
@AutoMapper(target = SysConfigEntity::class)
class SysConfigVo : BaseMixinVo(), SysConfigMixin {
    /**
     * 参数主键
     */
    @field:ExcelProperty(value = ["参数主键"])
    override var configId: Long? = null

    /**
     * 参数名称
     */
    @field:ExcelProperty(value = ["参数名称"])
    override var configName: String? = null

    /**
     * 参数键名
     */
    @field:ExcelProperty(value = ["参数键名"])
    override var configKey: String? = null

    /**
     * 参数键值
     */
    @field:ExcelProperty(value = ["参数键值"])
    override var configValue: String? = null

    /**
     * 系统内置（Y是 N否）
     */
    @field:ExcelProperty(value = ["系统内置"], converter = ExcelDictConvert::class)
    @field:ExcelDictFormat(dictType = "sys_yes_no")
    override var configType: String? = null

    /**
     * 备注
     */
    @field:ExcelProperty(value = ["备注"])
    override var remark: String? = null

    /**
     * 创建时间
     */
    @field:ExcelProperty(value = ["创建时间"])
    override var createTime: LocalDateTime? = null
}
