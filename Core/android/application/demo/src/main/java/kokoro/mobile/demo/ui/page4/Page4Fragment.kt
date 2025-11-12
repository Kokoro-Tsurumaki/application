package kokoro.mobile.demo.ui.page4

import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kokoro.mobile.demo.R
import kokoro.mobile.demo.base.BaseFragment


class Page4Fragment: BaseFragment() {
    override fun getViewId(): Int =R.layout.fragment_page4

    override fun onBundle(bundle: Bundle) {
    }

    override fun observerUI() {
    }
    var webView: WebView? = null

    override fun init(view: View, savedInstanceState: Bundle?) {
         webView = view.findViewById(R.id.webView)
        configureWebView(webView!!)
        webView?.loadUrl("https://kokoro.xj.cn/markdown")

    }

    // 配置 WebView 的方法
    private fun configureWebView(webView: WebView) {


        val settings = webView.getSettings()
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        // 缓存设置
        settings.setCacheMode(WebSettings.LOAD_DEFAULT) // 更合理的缓存策略
//        settings.setAppCacheEnabled(true)
//        settings.setAppCachePath(getContext()!!.getCacheDir().getAbsolutePath())


        // 文件访问设置
        settings.setAllowFileAccess(true)
        settings.setAllowContentAccess(true)


        // 视口设置
//        settings.setUseWideViewPort(true)
//        settings.setLoadWithOverviewMode(true)


        // 解决跳转浏览器问题 - 关键设置
        webView.setWebViewClient(CustomWebViewClient())
        webView.setWebChromeClient(WebChromeClient())
    }

    // 自定义 WebViewClient 防止跳转浏览器
    private class CustomWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            // 处理所有链接在 WebView 内打开
            view.loadUrl(request.getUrl().toString())
            return true
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            // 兼容旧版 Android
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            // 页面加载完成后的处理
            super.onPageFinished(view, url)
        }
    }
}