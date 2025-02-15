package org.zipper.common.core.framework.ip2area

interface Ip2AreaStarter {
    /**
     * @param ip ip地址
     */
    fun getAreaCity(ip: String?): String?
}