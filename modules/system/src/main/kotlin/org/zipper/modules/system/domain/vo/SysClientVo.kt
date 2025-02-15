package org.zipper.modules.system.domain.vo

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated
import com.alibaba.excel.annotation.ExcelProperty
import io.github.linpeilie.annotations.AutoMapper
import org.zipper.framework.excel.annotation.ExcelDictFormat
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.system.domain.entity.SysClientEntity
import org.zipper.modules.system.domain.mixin.SysClientMixin
import org.zipper.modules.system.excel.ExcelDictConvert


/**
 * 授权管理视图对象 sys_client
 *
 * @author Michelle.Chung
 * @date 2023-05-15
 */
@ExcelIgnoreUnannotated
@AutoMapper(target = SysClientEntity::class)
class SysClientVo : BaseMixinVo(), SysClientMixin {
    /**
     * id
     */
    @field:ExcelProperty(value = ["id"])
    override var id: Long? = null

    /**
     * 客户端id
     */
    @field:ExcelProperty(value = ["客户端id"])
    override var clientId: String? = null

    /**
     * 客户端key
     */
    @field:ExcelProperty(value = ["客户端key"])
    override var clientKey: String? = null

    /**
     * 客户端秘钥
     */
    @field:ExcelProperty(value = ["客户端秘钥"])
    override var clientSecret: String? = null

    /**
     * 授权类型
     */
    @field:ExcelProperty(value = ["授权类型"])
    var grantTypeList: List<String>? = null

    /**
     * 授权类型
     */
    override var grantType: String? = ""

    /**
     * 设备类型
     */
    override var deviceType: String? = null

    /**
     * token活跃超时时间
     */
    @field:ExcelProperty(value = ["token活跃超时时间"])
    override var activeTimeout: Long? = null

    /**
     * token固定超时时间
     */
    @field:ExcelProperty(value = ["token固定超时时间"])
    override var timeout: Long? = null

    /**
     * 状态（0正常 1停用）
     */
    @field:ExcelProperty(value = ["状态"], converter = ExcelDictConvert::class)
    @field:ExcelDictFormat(readConverterExp = "0=正常,1=停用")
    override var status: String? = null
}
