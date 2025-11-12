package kokoro.mobile.hachimi.ui.features.login.model

import java.util.Objects


data class LoginModel(
    val catId: String = "",
    val isAuth: Int = 0,
    val loginFaceIdentifyOnOff: Int = 0,
    val modeList: List<Mode> = listOf(),
    val modeRule: String = "",
    val refreshToken: String = "",
    val token: String = "",
    val userName: String = ""
) {
    data class Mode(
        val modeId: Int = 0,
        val modeName: String = "",
        val modeRule: String = ""
    )
}

data class UserRequest(val username:String, val password:String)

