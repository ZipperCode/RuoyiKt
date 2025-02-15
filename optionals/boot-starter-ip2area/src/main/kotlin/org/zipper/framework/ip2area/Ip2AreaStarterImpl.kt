package org.zipper.framework.ip2area

import cn.hutool.core.io.FileUtil
import cn.hutool.core.io.resource.ClassPathResource
import cn.hutool.core.net.NetUtil
import cn.hutool.core.util.ObjectUtil
import cn.hutool.http.HtmlUtil
import org.lionsoul.ip2region.xdb.Searcher
import org.springframework.stereotype.Component
import org.zipper.common.core.ext.log
import org.zipper.common.core.framework.ip2area.Ip2AreaStarter
import java.io.File

/**
 * ip区域查询实现
 */
@Component
class Ip2AreaStarterImpl : Ip2AreaStarter {

    private val searcher: Searcher by lazy {
        val fileName = "/ip2region.xdb"
        val existFile = File(FileUtil.getTmpDir().toString() + FileUtil.FILE_SEPARATOR + fileName)
        if (!FileUtil.exist(existFile)) {
            val fileStream = ClassPathResource(fileName)
            if (ObjectUtil.isEmpty(fileStream.stream)) {
                throw RuntimeException("RegionUtils初始化失败，原因：IP地址库数据不存在！")
            }
            FileUtil.writeFromStream(fileStream.stream, existFile)
        }

        val dbPath = existFile.path

        // 1、从 dbPath 加载整个 xdb 到内存。
        val cBuff: ByteArray
        try {
            cBuff = Searcher.loadContentFromFile(dbPath)
        } catch (e: Exception) {
            throw RuntimeException("RegionUtils初始化失败，原因：从ip2region.xdb文件加载内容失败！" + e.message)
        }
        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
        try {
            return@lazy Searcher.newWithBuffer(cBuff)
        } catch (e: Exception) {
            throw RuntimeException("RegionUtils初始化失败，原因：" + e.message)
        }
    }

    override fun getAreaCity(ip: String?): String? {
        if (ip.isNullOrEmpty()) {
            return "XX XX"
        }

        val result = if (ip.contains("0:0:0:0:0:0:0:1")) "127.0.0.1" else HtmlUtil.cleanHtmlTag(ip)
        if (NetUtil.isInnerIP(result)) {
            return "内网IP"
        }
        try {
            // 3、执行查询
            val region = searcher.search(ip.trim())
            return region.replace("0|", "").replace("|0", "")
        } catch (e: Exception) {
            log.error("IP地址离线获取城市异常 {}", ip)
            return "未知"
        }
    }
}