package kokoro.mobile.template

import android.content.Context
import android.content.Intent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import kokoro.mobile.contract.SharePlug
import kokoro.mobile.network.network
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

fun getApplicationContext(): Context = KokoroApplication.application.applicationContext

@HiltAndroidApp
class KokoroApplication : android.app.Application() {
    companion object {
        lateinit var application: KokoroApplication
    }

    override fun onCreate() {
        super.onCreate()
        SharePlug.application = this
        SharePlug.launchUI = {
            it.startActivity(Intent(it, _root_ide_package_.kokoro.mobile.template.ui.LauncherUI::class.java))
        }
        //应用Application初始化完成
        application = this
        network(this) {
            config{
                baseUrl = BuildTypeConstant.BASE_URL
                interceptors = BuildTypeConstant.interceptorList.apply {
                    add(_root_ide_package_.kokoro.mobile.template.core.TokenOutInterceptor())
                }
                converters =
                    mutableListOf(ScalarsConverterFactory.create())
            }
        }
//        AppDirectoryProvider.appDirectory =  "kokoro"
//        AppDirectoryProvider.fileProviderAuthorities =  "cn.xj.kokoro.mobile.fileprovider"
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
//        if (isRelease){
//            initAcra {
//                buildConfigClass = BuildConfig::class.java
//                reportFormat = StringFormat.JSON
//                dialog {
//                    title = "程序崩溃了"
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

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetRepository(): kokoro.mobile.template.core.network.NetRepository {
        return _root_ide_package_.kokoro.mobile.template.core.network.NetRepository(
            KokoroApplication.application
        )
    }


}