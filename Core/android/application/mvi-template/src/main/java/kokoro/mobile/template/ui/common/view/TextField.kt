package kokoro.mobile.template.ui.common.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kokoro.mobile.template.common.nonScaledSp
import kokoro.mobile.template.theme.color_d7dee0
import kokoro.mobile.template.theme.Color_999
import kokoro.mobile.template.theme.Color_999
import kokoro.mobile.template.theme.color_d7dee0

/**
 * Created by xianjie on 2025年1月13日10:27:34
 *
 * Description:
 * @param hint 提示语
 * @param width 宽度
 * @param keyboardType 输入类型
 * @param extentView 扩展view
 */
@Composable
fun LoginTextField(
    hint: String,
    content: String,
    width: Dp,
    keyboardType:KeyboardType = KeyboardType.Text,
    maxLength:Int? = null,
    extentView:(@Composable RowScope.()->Unit)? = null,
    valueChargeCallback:(String)->Unit,) {
    BasicTextField(
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(25.dp))
            .border(1.dp, color_d7dee0, RoundedCornerShape(25.dp))
            .size(width, 50.dp),

        textStyle = TextStyle(
            fontSize = 16.nonScaledSp,
            textAlign = TextAlign.Start,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType, // 仅数字键盘
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        value = content,
        onValueChange = {
            if (maxLength != null){
                if (it.length <= maxLength){
                    valueChargeCallback.invoke(it)
                }
            }else{
                valueChargeCallback.invoke(it)
            }

        },
        decorationBox = {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxSize(),
                horizontalArrangement  =  Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)// 输入框区域占用剩余空间
                ) {
                    if (content.isEmpty()) Text(text = hint, fontSize = 16.nonScaledSp, color = Color_999)
                    it()  // 输入框内容
                }
                extentView?.invoke(this)
            }
        }

    )
}

@Preview
@Composable
fun PreView(){
    LoginTextField("输入一些东西","请输入名称", 300.dp,extentView ={
        Text("获取名称")
    }){
    }
}