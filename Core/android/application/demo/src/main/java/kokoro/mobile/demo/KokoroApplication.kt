package kokoro.mobile.demo

import android.app.Application
import android.content.Context
import cn.xj.kokoro.mobile.album.AppDirectoryProvider
import kokoro.mobile.contract.SharePlug

fun getApplicationContext(): Context = KokoroApplication.application.applicationContext

class KokoroApplication : Application() {
    companion object {
        lateinit var application: KokoroApplication
    }

    override fun onCreate() {
        super.onCreate()
        SharePlug.application = this
        SharePlug.launchUI = {
        }
        //应用Application初始化完成
        application = this




        AppDirectoryProvider.appDirectory =  "kokoro"
        AppDirectoryProvider.fileProviderAuthorities =  "kokoro.mobile.demo.fileprovider"
    }


    override fun attachBaseContext(base: Context) { super.attachBaseContext(base)
//        if (isRelease){
//            initAcra {
//                buildConfigClass = BuildConfig::class.java
//                reportFormat = StringFormat.JSON
//                dialog {
//                    title = "程序崩溃了"/
//                    text = "一个不可预知的错误导致程序停止正常运行，上传日志帮助我们改进它"
//                    positiveButtonText = "上传错误日志"
//                    negativeButtonText = ""
//                    resIcon = null
//                    resTheme = R.style.DialogTheme
//                }
//
//            }
//        }
//        startService(Intent(this, MainService::class.java))
    }
}

