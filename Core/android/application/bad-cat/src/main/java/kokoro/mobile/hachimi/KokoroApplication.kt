package kokoro.mobile.hachimi

import android.app.Application
import android.content.Context
import android.content.Intent
import kokoro.mobile.hachimi.core.TokenOutInterceptor
import kokoro.mobile.hachimi.ui.LauncherUI
import kokoro.mobile.contract.SharePlug
import kokoro.mobile.hachimi.core.network.NetRepository
import kokoro.mobile.network.network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import kokoro.mobile.hachimi.database.DatabaseRepository
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import javax.inject.Singleton

fun getApplicationContext(): Context = KokoroApplication.application.applicationContext

@HiltAndroidApp
class KokoroApplication : Application() {
    companion object {
        lateinit var application: KokoroApplication
    }

    override fun onCreate() {
        super.onCreate()
        SharePlug.application = this
        SharePlug.launchUI = {
            it.startActivity(Intent(it, LauncherUI::class.java))
        }
        //应用Application初始化完成
        application = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
//        else {
//            Timber.plant(Timber.ReleaseTree())
//        }

        network(this) {
            config{
                baseUrl = BuildTypeConstant.BASE_URL
                interceptors = BuildTypeConstant.interceptorList.apply {
                    add(TokenOutInterceptor())
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
    fun provideNetRepository(): NetRepository {
        return NetRepository(KokoroApplication.application)
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(): DatabaseRepository {
        return DatabaseRepository(KokoroApplication.application)
    }

}