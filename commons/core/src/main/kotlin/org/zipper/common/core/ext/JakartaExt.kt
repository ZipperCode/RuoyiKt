package org.zipper.common.core.ext

import cn.hutool.core.io.IoUtil
import cn.hutool.core.util.StrUtil
import cn.hutool.http.HttpStatus
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.zipper.common.core.utils.FileUtils
import java.io.IOException
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * 下载文件名重新编码
 * @param realFileName  真实文件名
 */
fun HttpServletResponse.setAttachmentResponseHeader(realFileName: String) {
    val encode = URLEncoder.encode(realFileName, StandardCharsets.UTF_8)
    val percentEncodedFileName = encode.replace("\\+".toRegex(), "%20")
    val contentDispositionValue = "attachment; filename=%s;filename*=utf-8''%s".format(
        percentEncodedFileName, percentEncodedFileName
    )
    addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename")
    addHeader("Content-disposition", contentDispositionValue)
    addHeader("download-filename", percentEncodedFileName)
}

/**
 * 下载附件
 */
fun HttpServletResponse.writeAttachment(filename: String, content: ByteArray) {
    // 设置 header 和 contentType
    setHeader("Access-Control-Expose-Headers", "Content-Disposition")
    setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"))
    val contentType = FileUtils.getFileType(content, filename).mimeType
    this.contentType = contentType
    // 针对 video 的特殊处理，解决视频地址在移动端播放的兼容性问题
    if (StrUtil.containsIgnoreCase(contentType, "video")) {
        addHeader("Content-Length", (content.size - 1).toString())
        addHeader("Content-Range", (content.size - 1).toString())
        addHeader("Accept-Ranges", "bytes")
    }
    // 输出附件
    IoUtil.write(outputStream, false, content)
}

fun HttpServletRequest.getDecodeHeader(name: String?): String {
    val headerValue = getHeader(name)
    if (headerValue.isNullOrEmpty()) {
        return ""
    }
    return URLDecoder.decode(headerValue, StandardCharsets.UTF_8)
}

/**
 * 判断是否是一个ajax请求
 */
fun HttpServletRequest.isAjaxRequest(): Boolean {
    val accept = getHeader("Accept")
    if (StrUtil.equalsAnyIgnoreCase(accept, "application/json", "application/xml")) {
        return true
    }
    val uri = requestURI
    if (StrUtil.equalsAnyIgnoreCase(uri, ".josn", ".xml")) {
        return true
    }
    return StrUtil.containsAnyIgnoreCase(accept, getParameter("__ajax"))
}

fun HttpServletResponse.renderString(string: String?, contentType: String = "text/plain;charset=UTF-8") {
    try {
        this.status = HttpStatus.HTTP_OK
        this.contentType = contentType
        characterEncoding = StandardCharsets.UTF_8.toString()
        string?.let {
            writer.write(it)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}


