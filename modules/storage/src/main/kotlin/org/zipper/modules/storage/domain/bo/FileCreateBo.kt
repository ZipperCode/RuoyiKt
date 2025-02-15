package org.zipper.modules.storage.domain.bo

import io.github.linpeilie.annotations.AutoMapper
import org.zipper.modules.storage.domain.entity.SysFileRecordEntity
import org.zipper.modules.storage.domain.mixin.FileRecordMixin

/**
 * 文件创建业务对象
 */
@AutoMapper(target = SysFileRecordEntity::class, reverseConvertGenerate = false)
data class FileCreateBo constructor(
    override var configId: Long?,
    override var path: String?,
    override var url: String?,
    override var mimeType: String?,
    override var hash: String?,
    override var fileSize: Long,
    override var service: String? = null,
    var extName: String? = null
) : FileRecordMixin