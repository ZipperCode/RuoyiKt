package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity

/**
 * 字典数据表 sys_dict_data
 *
 */
@TableName("sys_dict_data")
open class SysDictDataEntity : BaseMixinEntity() {
    /**
     * 字典编码
     */
    @TableId(value = "dict_code")
    var dictCode: Long? = null

    /**
     * 字典排序
     */
    var dictSort: Int? = null

    /**
     * 字典标签
     */
    var dictLabel: String? = null

    /**
     * 字典键值
     */
    var dictValue: String? = null

    /**
     * 字典类型
     */
    var dictType: String? = null

    /**
     * 样式属性（其他样式扩展）
     */
    var cssClass: String? = null

    /**
     * 表格字典样式
     */
    var listClass: String? = null

    /**
     * 是否默认（Y是 N否）
     */

    var isDefault: String? = null

    fun setIsDefault(isDefault: String?) {
        this.isDefault = isDefault
    }

    fun getIsDefault(): String? = this.isDefault
    /**
     * 备注
     */
    var remark: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SysDictDataEntity) return false
        if (!super.equals(other)) return false

        if (dictCode != other.dictCode) return false
        if (dictSort != other.dictSort) return false
        if (dictLabel != other.dictLabel) return false
        if (dictValue != other.dictValue) return false
        if (dictType != other.dictType) return false
        if (cssClass != other.cssClass) return false
        if (listClass != other.listClass) return false
        if (isDefault != other.isDefault) return false
        if (remark != other.remark) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (dictCode?.hashCode() ?: 0)
        result = 31 * result + (dictSort ?: 0)
        result = 31 * result + (dictLabel?.hashCode() ?: 0)
        result = 31 * result + (dictValue?.hashCode() ?: 0)
        result = 31 * result + (dictType?.hashCode() ?: 0)
        result = 31 * result + (cssClass?.hashCode() ?: 0)
        result = 31 * result + (listClass?.hashCode() ?: 0)
        result = 31 * result + (isDefault?.hashCode() ?: 0)
        result = 31 * result + (remark?.hashCode() ?: 0)
        return result
    }


}
