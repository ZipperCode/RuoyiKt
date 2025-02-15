package org.zipper.modules.storage.enums

import org.zipper.common.core.framework.MessageThrowDelegate

object FileMessageConst {


    /**
     * 文件配置未找到
     */

    val ConfigNotFoundError: Exception by MessageThrowDelegate("store.file.config.not.found")

    /**
     * 主配置未找到
     */
    val MasterConfigNotFoundError: Exception by MessageThrowDelegate("store.file.config.master.not.found")

    /**
     * 主配置无法删除
     */
    val UnableDeleteMasterConfigError: Exception by MessageThrowDelegate("store.file.config.master.unable.delete")

    /**
     * 客户端未找到
     */
    val ClientNotFoundError: Exception by MessageThrowDelegate("store.file.client.not.found")
}