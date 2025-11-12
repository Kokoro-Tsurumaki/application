package kokoro.mobile.hachimi.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

object MoneyUtils {
    // 默认货币格式（人民币）
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.CHINA)
    private val decimalFormat = DecimalFormat("#0.00")

    /**
    * 将字符串金额转换为分（Long）
    * 支持格式："123.45" -> 12345, "123" -> 12300
    */
    fun stringToCents(amountStr: String): Long {
        return try {
            // 移除所有非数字字符（除小数点）
            val cleanStr = amountStr.replace("[^\\d.]".toRegex(), "")

            // 如果字符串为空或无效
            if (cleanStr.isEmpty()) return 0L

            // 检查是否有小数点
            if (cleanStr.contains('.')) {
                val parts = cleanStr.split('.')
                val yuan = parts[0].toLong()
                var fen = if (parts.size > 1) parts[1] else "0"

                // 确保分部分有两位
                fen = when (fen.length) {
                    0 -> "00"
                    1 -> fen + "0"
                    else -> fen.substring(0, 2) // 只取前两位
                }

                yuan * 100 + fen.toLong()
            } else {
                // 没有小数点，直接乘以100
                cleanStr.toLong() * 100
            }
        } catch (e: Exception) {
            0L // 转换失败返回0
        }
    }

    /**
    * 将分转换为元（带两位小数）
    * 12345 -> "123.45"
    */
    fun centsToYuanString(cents: Long): String {
        val yuan = cents / 100
        val fen = cents % 100
        return "$yuan.${fen.toString().padStart(2, '0')}"
    }

    /**
    * 将分转换为格式化货币字符串
    * 12345 -> "¥123.45"
    */
    fun centsToCurrencyString(cents: Long): String {
        return currencyFormat.format(cents.toDouble() / 100)
    }

    /**
    * 将元（Double）转换为分
    * 123.45 -> 12345
    */
    fun yuanToCents(yuan: Double): Long {
        return (yuan * 100).toLong()
    }

    /**
    * 将元（BigDecimal）转换为分
    * 提供精确转换
    */
    fun yuanToCents(yuan: BigDecimal): Long {
        return yuan.multiply(BigDecimal(100)).setScale(0, RoundingMode.HALF_EVEN).toLong()
    }

    /**
    * 将分转换为元（Double）
    */
    fun centsToYuan(cents: Long): Double {
        return cents.toDouble() / 100
    }

    /**
    * 将分转换为元（BigDecimal）
    * 提供精确值
    */
    fun centsToYuanBigDecimal(cents: Long): BigDecimal {
        return BigDecimal(cents).divide(BigDecimal(100), 2, RoundingMode.HALF_EVEN)
    }

    /**
    * 格式化金额（元）为字符串
    * 123.456 -> "123.46"
    */
    fun formatYuan(yuan: Double): String {
        return decimalFormat.format(yuan)
    }

    /**
    * 格式化金额（元）为货币字符串
    * 123.456 -> "¥123.46"
    */
    fun formatCurrency(yuan: Double): String {
        return currencyFormat.format(yuan)
    }

    /**
    * 安全加法（防止溢出）
    * 返回新的分值
    */
    fun safeAdd(cents1: Long, cents2: Long): Long {
        return try {
            Math.addExact(cents1, cents2)
        } catch (e: ArithmeticException) {
            Long.MAX_VALUE // 溢出时返回最大值
        }
    }

    /**
    * 安全减法（防止溢出）
    * 返回新的分值
    */
    fun safeSubtract(cents1: Long, cents2: Long): Long {
        return try {
            Math.subtractExact(cents1, cents2)
        } catch (e: ArithmeticException) {
            Long.MIN_VALUE // 溢出时返回最小值
        }
    }

    /**
    * 安全乘法（防止溢出）
    * 数量 * 单价
    */
    fun safeMultiply(quantity: Int, unitPriceCents: Long): Long {
        return try {
            Math.multiplyExact(quantity.toLong(), unitPriceCents)
        } catch (e: ArithmeticException) {
            Long.MAX_VALUE // 溢出时返回最大值
        }
    }

    /**
    * 计算折扣价
    * @param originalCents 原价（分）
    * @param discountRate 折扣率（0-1之间的小数）
    */
    fun calculateDiscount(originalCents: Long, discountRate: Double): Long {
        require(discountRate in 0.0..1.0) { "折扣率必须在0-1之间" }
        return (originalCents * discountRate).toLong()
    }

    /**
    * 计算含税价格
    * @param amountCents 金额（分）
    * @param taxRate 税率（0.13表示13%）
    */
    fun calculateTaxIncluded(amountCents: Long, taxRate: Double): Long {
        require(taxRate >= 0) { "税率不能为负数" }
        return (amountCents * (1 + taxRate)).toLong()
    }

    /**
    * 计算百分比
    * @param partCents 部分金额（分）
    * @param totalCents 总金额（分）
    */
    fun calculatePercentage(partCents: Long, totalCents: Long): Double {
        if (totalCents == 0L) return 0.0
        return partCents.toDouble() / totalCents * 100
    }

    /**
    * 分列表求和
    */
    fun sumCents(amounts: List<Long>): Long {
        return amounts.fold(0L) { acc, value -> safeAdd(acc, value) }
    }

    /**
    * 分列表平均值
    */
    fun averageCents(amounts: List<Long>): Long {
        if (amounts.isEmpty()) return 0L
        return sumCents(amounts) / amounts.size
    }

    /**
    * 验证金额字符串格式
    */
    fun isValidMoneyString(input: String): Boolean {
        return try {
            val clean = input.replace("[^\\d.]".toRegex(), "")
            if (clean.isEmpty()) return false

            val parts = clean.split('.')
            when (parts.size) {
                1 -> parts[0].toLong() >= 0
                2 -> {
                    parts[0].toLong() >= 0 &&
                            parts[1].length <= 2 &&
                            parts[1].toInt() >= 0
                }
                else -> false
            }
        } catch (e: Exception) {
            false
        }
    }

    /**
    * 将分转换为中文大写金额
    */
    fun centsToChinese(cents: Long): String {
        val yuan = cents / 100
        val fen = cents % 100

        val yuanStr = numberToChinese(yuan)
        val fenStr = if (fen > 0) numberToChinese(fen) else ""

        return when {
            yuan > 0 && fen > 0 -> "$yuanStr 元${fenStr}分"
            yuan > 0 -> "$yuanStr 元整"
            fen > 0 -> "${fenStr}分"
            else -> "零元整"
        }
    }

    private fun numberToChinese(number: Long): String {
        val units = listOf("", "拾", "佰", "仟")
        val bigUnits = listOf("", "万", "亿", "兆")
        val digits = listOf("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖")

        if (number == 0L) return "零"

        val numStr = number.toString()
        val len = numStr.length
        var result = StringBuilder()
        var zeroFlag = false // 标记前一位是否是零

        for (i in 0 until len) {
            val digit = numStr[i] - '0'
            val unitIndex = (len - 1 - i) % 4
            val bigUnitIndex = (len - 1 - i) / 4

            if (digit == 0) {
                zeroFlag = true
            } else {
                if (zeroFlag) {
                    result.append("零")
                    zeroFlag = false
                }
                result.append(digits[digit])
                if (unitIndex > 0) {
                    result.append(units[unitIndex])
                }
            }

            // 添加大单位（万、亿等）
            if (unitIndex == 0 && bigUnitIndex > 0) {
                if (result.isNotEmpty() && result.last() == '零') {
                    result.deleteCharAt(result.length - 1)
                }
                result.append(bigUnits[bigUnitIndex])
            }
        }

        // 处理末尾的零
        if (result.endsWith("零")) {
            result.deleteCharAt(result.length - 1)
        }

        return result.toString()
    }
}