package kokoro.mobile.hachimi.ui.common.base

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

/**
 * Created by xianjie on 2025年1月21日09:33:19
 *
 * Description:
 */
class CommonWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : WebView(context, attrs) {

    init {
        initSettings()
    }

    private fun initSettings() {
        settings.apply {
            // 基础设置
            javaScriptEnabled = true  // 启用 JavaScript
            domStorageEnabled = true  // 启用 DOM storage API
            databaseEnabled = true    // 启用数据库存储API

            // 缓存设置
            cacheMode = WebSettings.LOAD_DEFAULT  // 默认缓存模式

            // 渲染设置
            setLayerType(LAYER_TYPE_HARDWARE, null)  // 硬件加速
            useWideViewPort = true    // 支持 viewport meta tag
            loadWithOverviewMode = true  // 自适应屏幕

            // 渲染优化
            blockNetworkImage = false  // 允许加载图片
            loadsImagesAutomatically = true // 允许自动加载图片
            mediaPlaybackRequiresUserGesture = false  // 允许自动播放媒体
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW  // 允许混合内容

            // 文件访问
            allowFileAccess = true
            allowContentAccess = true

            // 字体设置
            standardFontFamily = "sans-serif"
            defaultFontSize = 16
        }

        // 设置 WebViewClient
        webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // 页面开始加载回调
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // 页面加载完成回调
            }

            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?,
            ): WebResourceResponse? {
                // 资源请求拦截
                return super.shouldInterceptRequest(view, request)
            }
        }

        // 设置 WebChromeClient
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                // 加载进度回调
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                // 接收标题回调
            }
        }
    }

    fun clearWebViewCache() {
        clearCache(true)
        clearHistory()
        clearFormData()
        CookieManager.getInstance().removeAllCookies(null)
        clearSslPreferences()
    }
}


object WebViewPreloader {
    private var preloadedWebView: CommonWebView? = null

    fun preload(context: Context) {
        if (preloadedWebView == null) {
            preloadedWebView = CommonWebView(context.applicationContext).apply {
                // 预加载常用资源
                loadUrl("about:blank")
            }
        }
    }

    fun getPreloadedWebView(): CommonWebView? = preloadedWebView
}


// Compose 集成
@Composable
fun ModernWebView(
    url: String,
    modifier: Modifier = Modifier,
) {
    var webView by remember { mutableStateOf<CommonWebView?>(null) }
    var progress by remember { mutableIntStateOf(0) }

    Box(modifier = modifier) {
        AndroidView(
            factory = { context ->
                CommonWebView(context).apply {
                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            progress = newProgress
                        }
                    }
                    loadUrl(url)
                }.also { webView = it }
            },
            modifier = Modifier.fillMaxSize()
        )

        // 显示加载进度
        if (progress < 100) {
            LinearProgressIndicator(
                progress = progress / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .align(Alignment.TopCenter)
            )
        }
    }

    // 生命周期管理
    DisposableEffect(Unit) {
        onDispose {
            webView?.apply {
                stopLoading()
                clearWebViewCache()
                destroy()
            }
        }
    }
}






