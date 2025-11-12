package kokoro.mobile.contract

import android.content.Context

object SharePlug {
    var application: Context? = null

     var launchUI:((Context)->Unit)? = null

    fun launchComposeUI(context: Context){
        launchUI?.invoke(context)
    }
}