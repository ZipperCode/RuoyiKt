package org.zipper.modules.storage.service

import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.modules.storage.client.FileClient
import org.zipper.modules.storage.domain.param.FileConfigPageParam
import org.zipper.modules.storage.domain.param.FileConfigSaveParam
import org.zipper.modules.storage.domain.vo.FileConfigVo

interface FileConfigService {
    /**
     * 创建文件配置
     */
    fun createConfig(fileConfigSaveParam: FileConfigSaveParam): Int

    /**
     * 保存文件配置
     */
    fun updateConfig(fileConfigSaveParam: FileConfigSaveParam): Int

    /**
     * 删除配置
     * @param ids 配置id
     */
    fun deleteConfig(ids: Array<Long>): Int

    /**
     * 获取具体一配置
     */
    fun getConfig(id: Long): FileConfigVo?

    /**
     * 分页列表
     */
    fun pageList(param: FileConfigPageParam): TableDataInfo<FileConfigVo>

    /**
     * 更新文件配置为 Master
     *
     * @param id 编号
     */
    fun updateMaster(id: Long)

    /**
     * 获取文件客户端
     * @throws Exception 未找到抛出异常
     */
    fun getFileClient(clientId: Long): FileClient

    /**
     * 获取主文件客户端
     * @throws Exception 未找到抛出异常
     */
    fun getMasterFileClient(): FileClient
}