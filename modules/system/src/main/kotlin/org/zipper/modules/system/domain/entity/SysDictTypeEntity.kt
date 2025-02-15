package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity

/**
 * 字典类型表 sys_dict_type
 *
 */
@TableName("sys_dict_type")
class SysDictTypeEntity : BaseMixinEntity() {
    /**
     * 字典主键
     */
    @TableId(value = "dict_id", type = IdType.AUTO)
    var dictId: Long? = null

    /**
     * 字典名称
     */
    var dictName: String = ""

    /**
     * 字典类型
     */
    var dictType: String = "'"

    /**
     * 备注
     */
    var remark: String? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SysDictTypeEntity) return false
        if (!super.equals(other)) return false

        if (dictId != other.dictId) return false
        if (dictName != other.dictName) return false
        if (dictType != other.dictType) return false
        if (remark != other.remark) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (dictId?.hashCode() ?: 0)
        result = 31 * result + dictName.hashCode()
        result = 31 * result + dictType.hashCode()
        result = 31 * result + (remark?.hashCode() ?: 0)
        return result
    }


}
