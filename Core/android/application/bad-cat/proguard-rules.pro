-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

-keepattributes SourceFile,LineNumberTable

-renamesourcefileattribute SourceFile

# 基本保留规则
-keepclassmembers class * {
    @androidx.compose.runtime.internal.ComposableLambda *;
}

# 保留 Compose 相关组件
-keep class androidx.compose.runtime.Composable { *; }
-keep class androidx.compose.ui.Modifier { *; }
-keep class androidx.compose.ui.graphics.* { *; }
-keep class androidx.compose.material3.* { *; }
-keep class androidx.compose.foundation.* { *; }

# 保留 Compose 预览功能
-keep class * implements androidx.compose.ui.tooling.preview.PreviewParameterProvider { *; }
-keep @androidx.compose.ui.tooling.preview.Preview class * { *; }

# Room 数据库保留规则
-keep class * extends androidx.room.RoomDatabase { *; }
-keep class * extends androidx.room.Entity { *; }
-keep class * extends androidx.room.Dao { *; }
-keep class * extends androidx.room.TypeConverter { *; }

# Room 查询方法保留
-keepclassmembers class * {
    @androidx.room.* *;
}

# Hilt/Dagger 依赖注入保留规则
-keep class * extends dagger.hilt.android.internal.GeneratedComponent { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponentManager { *; }
-keep class * extends dagger.hilt.internal.ComponentSupplier { *; }
-keep class * extends dagger.hilt.internal.AliasOf { *; }
-keep class * extends dagger.hilt.internal.InstallIn { *; }
-keep class * extends dagger.hilt.internal.QualifierMetadata { *; }
-keep class * extends dagger.hilt.internal.ScopeMetadata { *; }
-keep class * extends dagger.hilt.internal.TestInstallIn { *; }
-keep class * extends dagger.hilt.internal.earlyentrypoint.AggregatedEarlyEntryPoint { *; }
-keep class * extends dagger.hilt.internal.uninstallmodules.AggregatedUninstallModules { *; }

# Hilt 生成的代码保留
-keep class * extends dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager { *; }
-keep class * extends dagger.hilt.android.internal.managers.ActivityComponentManager { *; }
-keep class * extends dagger.hilt.android.internal.managers.FragmentComponentManager { *; }

# Hilt 注解处理器生成的代码
-keep class hilt_aggregated_deps.* { *; }
-keep class *_HiltModules { *; }
-keep class *_HiltComponents { *; }
-keep class *_Factory { *; }
-keep class *_Impl { *; }

# Navigation 组件保留
-keep class * extends androidx.navigation.NavArgs { *; }
-keep class * extends androidx.navigation.NavDirections { *; }
-keep class * extends androidx.navigation.NavType { *; }

# Compose Navigation 保留
-keep class * implements androidx.navigation.compose.ComposableNavGraphBuilder { *; }

# Coil 图片加载库保留
-keep class coil.* { *; }
-keep class coil.bitmap.* { *; }
-keep class coil.decode.* { *; }
-keep class coil.fetch.* { *; }
-keep class coil.transform.* { *; }
-keep class coil.request.* { *; }

# Timber 日志库保留
-keep class timber.log.Timber { *; }
-keep class timber.log.Timber$Tree { *; }

# DataStore 保留
-keep class androidx.datastore.* { *; }
-keep class androidx.datastore.core.* { *; }
-keep class androidx.datastore.preferences.* { *; }

# Lifecycle 组件保留
-keep class androidx.lifecycle.* { *; }
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keep class * implements androidx.lifecycle.DefaultLifecycleObserver { *; }

# 保留 Kotlin 协程相关
-keep class kotlinx.coroutines.* { *; }
-keep class kotlin.coroutines.* { *; }

# 保留 Kotlin 元数据
-keep class kotlin.Metadata { *; }

# 保留序列化相关（如果有）
-keep class * implements kotlinx.serialization.Serializable { *; }

# 保留网络框架（基于您的 :framework:network 模块）
-keep class * implements retrofit2.http.* { *; }
-keep class * extends retrofit2.Call { *; }
-keep class * extends okhttp3.OkHttpClient { *; }
-keep class * extends okhttp3.Interceptor { *; }
-keep class * extends okhttp3.Response { *; }

# 保留 JSON 解析相关（如果有 Gson/Moshi）
-keep class * implements com.google.gson.TypeAdapter { *; }
-keep class * implements com.squareup.moshi.JsonAdapter { *; }

# 保留项目特定模块
-keep class com.yourpackage.framework.shell.* { *; }
-keep class com.yourpackage.framework.contract.* { *; }
-keep class com.yourpackage.framework.network.* { *; }

# 保留数据类（基于 @Parcelize 或 Serializable）
-keep class * implements android.os.Parcelable { *; }
-keep class * implements java.io.Serializable { *; }

# 保留注解（重要！）
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }

# 保留资源类（R8 通常会处理，但明确指定更安全）
-keepclassmembers class **.R$* {
    public static <fields>;
}

# 保留本地方法（JNI）
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留自定义视图和组件
-keep public class * extends android.view.View { *; }
-keep public class * extends android.app.Activity { *; }
-keep public class * extends android.app.Fragment { *; }
-keep public class * extends android.app.Service { *; }
-keep public class * extends android.content.BroadcastReceiver { *; }
-keep public class * extends android.content.ContentProvider { *; }

# 保留 Compose 可组合函数（确保运行时能找到）
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}

# 保留 Lambda 表达式（Compose 大量使用）
-keepclassmembers class * {
    public static * lambda$*;
}