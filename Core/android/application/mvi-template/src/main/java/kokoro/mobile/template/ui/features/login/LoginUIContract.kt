package kokoro.mobile.template.ui.features.login
import kokoro.mobile.template.ui.common.base.UIContract

/**
 * Created by xianjie on 2025年1月18日00:09:13
 *
 * Description:
 */
class LoginUIContract: UIContract {
    data class LoginState (
        //NET DATA
        //图片匹配文字
        var imageCode:String = "",
        // 图片
        var imageByteArray: ByteArray = byteArrayOf(),

        //LOCAL DATA
        //手机号码
        var phoneNumber:String = "",
        //图形验证码
        var authImageCode:String = "",
        //手机发送验证码
        var authCode:String = "",
        //用户名
        var userName:String ="",
        //密码
        var password:String = "",
        //是验证登录还是密码登录 ture 验证 false 密码
        var type:Boolean = true,
        //验证码倒计时
        var time:Int = 0,
        //是否首次注册
        var firstLogin:Boolean = false,
        //隐私协议选中
        var checked:Boolean = false
    ): UIContract.UIState{
        fun checkInput(): String {
            if (type){
                when {
                    phoneNumber.isBlank() -> return "请输入手机号"

                    phoneNumber.length != 11 -> return "手机号填写不正确"

                    authImageCode.isBlank() -> return "请输入图片验证码"

                    authImageCode.length != 4 -> return "验证码错误"

                    !checked -> return "请阅读同意并勾选协议"
                }
            }else{
                when {
                    userName.isBlank() -> return "请输入手机号"

                    userName.length != 11 -> return "用户名填写不正确"

                    password.isBlank() -> return "请输入密码"

                    !checked -> return "请阅读同意并勾选协议"
                }
            }

            return ""
        }
    }



    sealed class LoginAction: UIContract.UIAction {
        //登录
        data object OnLoginClick : LoginAction()
        //图片 刷新
        data object OnImageClick : LoginAction()
        //图片验证 获取二维码
        data object OnImageAuthClick : LoginAction()
        //登录类型
        data object OnTypeClick : LoginAction()
        //手机号更新
        data class OnPhoneNumberUpdate(val value:String) :LoginAction()
        //图片验证码更新
        data class OnImageAuthUpdate(val value:String) :LoginAction()
        //验证码更新
        data class OnAuthUpdate(val value:String) :LoginAction()
        //用户名更新
        data class OnUserNameUpdate(val value:String) :LoginAction()
        //密码更新
        data class OnPasswordUpdate(val value:String) :LoginAction()


        //隐私协议选中
        data object OnCheckUpdate :LoginAction()
        //隐私协议
        data object OnPrivacyPolicyClick :LoginAction()
        //用户协议
        data object OnUserAgreementClick :LoginAction()

    }
}