package org.zipper.framework.web.utils

import cn.hutool.captcha.generator.CodeGenerator
import cn.hutool.core.math.Calculator
import cn.hutool.core.util.CharUtil
import cn.hutool.core.util.RandomUtil
import org.apache.commons.lang3.StringUtils
import java.io.Serial
import kotlin.math.max
import kotlin.math.min

/**
 * 无符号计算生成器
 *
 * @author Lion Li
 */
class UnsignedMathGenerator @JvmOverloads constructor(
    /**
     * 参与计算数字最大长度
     */
    private val numberLength: Int = 2
) : CodeGenerator {
    /**
     * 构造
     *
     * @param numberLength 参与计算最大数字位数
     */

    override fun generate(): String {
        val limit = limit
        val a = RandomUtil.randomInt(limit)
        val b = RandomUtil.randomInt(limit)
        var max = max(a.toDouble(), b.toDouble()).toString()
        var min = min(a.toDouble(), b.toDouble()).toString()
        max = StringUtils.rightPad(max, this.numberLength, CharUtil.SPACE)
        min = StringUtils.rightPad(min, this.numberLength, CharUtil.SPACE)

        return max + RandomUtil.randomChar(OPERATORS) + min + '='
    }

    override fun verify(code: String, userInputCode: String): Boolean {
        val result: Int
        try {
            result = userInputCode.toInt()
        } catch (e: NumberFormatException) {
            // 用户输入非数字
            return false
        }

        val calculateResult = Calculator.conversion(code).toInt()
        return result == calculateResult
    }

    val length: Int
        /**
         * 获取验证码长度
         *
         * @return 验证码长度
         */
        get() = this.numberLength * 2 + 2

    private val limit: Int
        /**
         * 根据长度获取参与计算数字最大值
         *
         * @return 最大值
         */
        get() = ("1" + StringUtils.repeat('0', this.numberLength)).toInt()

    companion object {
        @Serial
        private val serialVersionUID = -5514819971774091076L

        private const val OPERATORS = "+-*"
    }
}
