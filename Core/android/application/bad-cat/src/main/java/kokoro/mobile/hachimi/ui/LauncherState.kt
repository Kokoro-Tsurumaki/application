package kokoro.mobile.hachimi.ui

import android.net.Uri

/**
 * Created by xianjie on 2025年1月7日13:45:01
 *
 * Description:
 */

data class LauncherState(
    val currentFlag:String,
)

sealed class Screen(val route: String) {
    //Token校验中转页
    data object Welcome : Screen("welcome")
    //主页
    data object Main : Screen("main")
    //登录
    data object Login : Screen("login")
    //授权页
    data object Authorize : Screen("authorize")
    //web
    data object Web : Screen("web&url={url}&name={name}"){
        fun createRoute(url:String,name:String) = "web&url=${Uri.encode(url)}&name=$name"
    }


}