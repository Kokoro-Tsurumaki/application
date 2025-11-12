package kokoro.mobile.hachimi.ui.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import kokoro.mobile.hachimi.common.ViewWithExtent
import kokoro.mobile.hachimi.common.nonScaledSp
import kokoro.mobile.hachimi.R
import kokoro.mobile.hachimi.common.Modifier.scaffoldModifier
import kokoro.mobile.hachimi.theme.Black
import kokoro.mobile.hachimi.theme.Color333
import kokoro.mobile.hachimi.theme.MainThemeColor
import kokoro.mobile.hachimi.theme.Yellow
import kokoro.mobile.hachimi.ui.PreviewContainer
import kokoro.mobile.hachimi.ui.common.AnnotatedString
import kokoro.mobile.hachimi.ui.common.AnnotatedText
import kokoro.mobile.hachimi.ui.common.base.UIContract
import kokoro.mobile.hachimi.ui.common.base.VMStateContainer
import kokoro.mobile.hachimi.ui.common.view.ByteArrayImage
import kokoro.mobile.hachimi.ui.common.view.CustomCheckBox
import kokoro.mobile.hachimi.ui.common.view.LoginTextField
import kokoro.mobile.hachimi.ui.common.view.specific.HachimiFlag
import kokoro.mobile.hachimi.ui.features.login.LoginUIContract.LoginAction


/**
 * Created by xianjie on 2025年1月8日09:22:21
 *
 * Description:
 */
@Composable
fun LoginScreen() {
    VMStateContainer<LoginViewModel>(onInit = { viewModel ->

    }, effectCallBack = {

    }) {
        val viewModel: LoginViewModel = viewModel()

        //状态
        val uiState by viewModel.uiFlow.collectAsStateWithLifecycle()
        LoginView(uiState){
            viewModel.sendUIAction(it)
        }
    }
}

@Composable
fun LoginView(state: LoginUIContract.LoginState, onAction:((LoginAction)-> Unit) ) {
    Scaffold(modifier = scaffoldModifier, content = { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(200.dp))
            HachimiFlag(Modifier.align(Alignment.CenterHorizontally))

//            if (state.type) AuthenticationLogin(state, onAction)
//            else
                PassWordLogin(state, onAction)


            Button(
                onClick = {
                    onAction(LoginAction.OnLoginClick)
                },
                modifier = Modifier
                    .size(312.dp, 40.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(25.dp),
                        clip = true
                    )
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Text(text = "登录", fontSize = 14.nonScaledSp)
            }
            Spacer(modifier = Modifier.height(12.dp))
//            Row(
//                modifier = Modifier.align(Alignment.Start),
//                horizontalArrangement = Arrangement.Start,
//            ) {
//                Spacer(modifier = Modifier.width(32.dp))
//                ViewWithExtent(PaddingValues(10.dp), view = {
//                    Text(
//                        text = if (state.type) "密码登录" else "手机登录",
//                        fontSize = 14.nonScaledSp,
//                        color = Color333
//                    )
//                }) {
//                    onAction(LoginUIContract.LoginAction.OnTypeClick)
//                }
//
//            }

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Column {
                    PrivacyPolicyWithCheckbox(state, onAction)
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
//            PrivacyDialog()
        }
    })
}

@Composable
fun PrivacyDialog() {
    var showDialog = true
    Dialog(
        onDismissRequest = { showDialog = false },
        properties = DialogProperties(
            usePlatformDefaultWidth = false  // 禁用平台默认宽度
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("欢迎使用雨欣智慧APP", fontSize = 18.nonScaledSp, color = Black)
                Spacer(Modifier.height(16.dp))
                Text(
                    "      我们承诺会采取业界先进的安全措施保护您的信息安全，在您使用本应用时，我们会向您申请获取定位、相机、相册、日历、麦克风、通知等权限，您有权拒绝或取消授权，您同意本弹窗内容相应设备权限并不会默认开启，我们会在您使用到相应业务功能时, 另行弹窗征得您的同意后开启，请知悉。",
                    fontSize = 15.nonScaledSp,
                    color = Black,
                    style = TextStyle(
                        lineHeight = 18.nonScaledSp  // 设置固定行高
                    )
                )
                Spacer(Modifier.height(16.dp))
                Button(onClick = { showDialog = false }) {
                    Text("关闭")
                }
            }
        }
    }
}

@Composable
fun PrivacyPolicyWithCheckbox(
    state: LoginUIContract.LoginState, onAction:((LoginAction)-> Unit)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        CustomCheckBox(state.checked) {
            onAction(LoginAction.OnCheckUpdate)
        }
        Spacer(modifier = Modifier.width(10.dp))

        val content =
            "我已阅读并同意《耄耋记账APP隐私政策》\n《耄耋用户服务协议》并授权耄耋获得本机号码"
        val annotatedString = AnnotatedString.buildAnnotatedString(
            content,
            hashMapOf(
                "《耄耋记账APP隐私政策》" to "privacy_policy", "" +
                        "《耄耋用户服务协议》" to "user_agreement"),
            SpanStyle(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textDecoration = TextDecoration.Underline
            )
        )

        AnnotatedText(annotatedString) { tag ->
            if (tag == "privacy_policy") {
                onAction(LoginAction.OnPrivacyPolicyClick)
            } else if (tag == "user_agreement") {
                onAction(LoginAction.OnUserAgreementClick)
            }
        }
    }
}


@Composable
fun AuthenticationLogin(state: LoginUIContract.LoginState, onAction:((LoginAction)-> Unit)) {
    Spacer(modifier = Modifier.height(22.dp))

    Column(modifier = Modifier.wrapContentHeight()) {
        LoginTextField(
            "请输入您的手机号码", state.phoneNumber, 320.dp, KeyboardType.Phone, 11
        ) {
            onAction(LoginAction.OnPhoneNumberUpdate(it))
        }
        if (state.firstLogin)
            Text(
                text = "登录后,您的手机号将被注册为雨欣智慧app用户",
                fontSize = 10.nonScaledSp,
                color = Yellow
            )
        else Spacer(modifier = Modifier.height(14.dp))

    }
    Row(
        modifier = Modifier.size(320.dp, 50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LoginTextField(
            "输入图形验证码", state.authImageCode, 190.dp, KeyboardType.Text, 4
        ) {
            onAction(LoginAction.OnImageAuthUpdate(it))
        }

        ByteArrayImage(state.imageByteArray) {
            onAction(LoginAction.OnImageClick)
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
    LoginTextField(
        "请输入您的验证码",
        state.authCode,
        320.dp,
        KeyboardType.Number,
        4,
        extentView = {
            ViewWithExtent(PaddingValues(10.dp), view = {
                Text(
                    text = if (state.time == 0) "获取验证码" else "${state.time}s",
                    fontSize = 14.nonScaledSp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }) {
                if (state.time == 0) onAction(LoginAction.OnImageAuthClick)
            }
        }) {
        onAction(LoginAction.OnAuthUpdate(it))
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun PassWordLogin(state: LoginUIContract.LoginState, onAction:((LoginAction)-> Unit)) {
    Spacer(modifier = Modifier.height(45.dp))

    LoginTextField(
        "请输入您的用户名", state.userName, 320.dp, KeyboardType.Phone,
    ) {
        onAction(LoginAction.OnUserNameUpdate(it))
    }

    Spacer(modifier = Modifier.height(25.dp))

    LoginTextField("请输入您的密码", state.password, 320.dp, KeyboardType.Password) {
        onAction(LoginAction.OnPasswordUpdate(it))
    }
    Spacer(modifier = Modifier.height(20.dp))
}


@Preview
@Composable
fun PreView() {
    PreviewContainer {
        LoginView(LoginUIContract.LoginState()){

        }
    }
}




