package kokoro.mobile.hachimi.ui.common

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import kokoro.multiplatform.sasanqua.common.nonScaledSp

/**
 * Created by xianjie on 2025年1月22日11:26:42
 *
 * Description:富文本封装
 */
object AnnotatedString {
    /**
     * @param annotatedString 富文本实例
     * @param rule 规则
     */
    data class AnnotatedModel(val annotatedString: AnnotatedString, val rule: Map<String, String>)

    /**
     * 一个带有点击事件的富文本
     * @param content 全部内容
     * @param rule 规则 你要改变的内容
     */
    fun buildAnnotatedString(
        content: String,
        rule: Map<String, String>,
        style: SpanStyle
    ): AnnotatedModel {
        val splitMap = content.splitWithMatch(rule)
        return AnnotatedModel(buildAnnotatedString {
            splitMap.forEach { element ->
                if (element.value.isBlank()) {
                    append(element.key)
                } else {
                    pushStringAnnotation(tag = element.value, annotation = "")

                    withStyle(
                        style = style
                    ) {
                        append(element.key)
                    }

                    pop()
                }
            }
        }, rule)

    }

    /**
     * 处理富文本
     */
    private fun String.splitWithMatch(patterns: Map<String, String>): Map<String, String> {
        val regex = patterns.keys.joinToString("|") {
            Regex.escape(it)
        }.toRegex()

        val result = LinkedHashMap<String, String>()
        var lastIndex = 0

        // 查找所有匹配项
        regex.findAll(this).forEach { matchResult ->
            // 处理匹配项之前的字符
            (lastIndex until matchResult.range.first).forEach { index ->
                result[this[index].toString()] = ""
            }

            // 处理匹配项
            val match = matchResult.value
            result[match] = patterns[match] ?: ""

            lastIndex = matchResult.range.last + 1
        }

        // 处理最后一个匹配项之后的字符
        (lastIndex until length).forEach { index ->
            result[this[index].toString()] = ""
        }

        return result
    }
}

/**
 * 处理点击事件的富文本控件
 */
@Composable
fun AnnotatedText(
    model: AnnotatedString.AnnotatedModel,
    style: TextStyle = TextStyle(fontSize = 12.nonScaledSp,color = MaterialTheme.colorScheme.onBackground),
    modifier: Modifier = Modifier,
    clickCallback: (tag: String) -> Unit
) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    Text(
        text = model.annotatedString, style = style, onTextLayout = { layoutResult ->
            textLayoutResult = layoutResult
        }, modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    textLayoutResult?.let { layoutResult ->
                        val position = layoutResult.getOffsetForPosition(offset)
                        model.rule.values.forEach { element ->
                            if (element.isNotBlank()) {
                                model.annotatedString
                                    .getStringAnnotations(
                                        tag = element,
                                        start = position,
                                        end = position
                                    )
                                    .firstOrNull()
                                    ?.let {
                                        clickCallback.invoke(element)
                                    }
                            }
                        }
                    }

                }
            })
}