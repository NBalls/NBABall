package ball.drive.com.nbaball

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.webkit.WebSettings
import android.webkit.WebView
import me.yokeyword.fragmentation.SupportActivity

class WebActivity : SupportActivity() {

    companion object {
        val EXTRA_URL = "extra_url"

        fun startWebActivity(context: Context, webUrl: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(EXTRA_URL, webUrl)
            context.startActivity(intent)
        }
    }

    private val webView by lazy {
        this.findViewById<WebView>(R.id.webView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        //支持javascript
        webView.settings.javaScriptEnabled = true
        // 设置可以支持缩放
        webView.settings.setSupportZoom(true)
        // 设置出现缩放工具
        webView.settings.builtInZoomControls = true
        //扩大比例的缩放
        webView.settings.useWideViewPort = true
        //自适应屏幕
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN

        val webUrl = intent.getStringExtra(EXTRA_URL)
        if (!TextUtils.isEmpty(webUrl)) {
            webView.loadUrl(webUrl)
        }
    }
}
