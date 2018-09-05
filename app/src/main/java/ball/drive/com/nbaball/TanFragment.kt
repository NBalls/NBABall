package ball.drive.com.nbaball

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.JavascriptInterface
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import ball.drive.com.nbaball.WebActivity.Companion.startWebActivity
import ball.drive.com.nbaball.qiutan.MClass
import ball.drive.com.nbaball.qiutan.MainBean
import ball.drive.com.nbaball.utils.changeNumIndex
import ball.drive.com.nbaball.utils.getYMD
import ball.drive.com.nbaball.utils.getYMDHMS
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.onClick
import org.jetbrains.anko.runOnUiThread
import org.jsoup.Jsoup

/**
 * Created by aaron on 2018/4/11.
 * 解析QT数据
 */
class TanFragment: BaseFragment() {

    companion object {
        val SHARE_NAME = "parser_data"
        val SP_TIME_KEY = "sp_time_key"
        val SP_DATE_KEY = "sp_date_key"
        val SP_DATA_KEY = "sp_data_key"

        val TAG = "TanFragment"
        val DEFAULT_WEB_URL = "http://live.titan007.com"
        val DEFAULT_VALIDATE_URL = "http://www.win0168.com/football/hg/Over_"
        val DEFAULT_HTML_PRE = "<html><head></head><body>"
        val DEFAULT_HTML_LAST = "</body></html>"
    }

    private val webView by lazy {
        view?.findViewById<WebView>(R.id.webView)
    }
    private val webView2 by lazy {
        view?.findViewById<WebView>(R.id.webView2)
    }
    private val sharedPreferences by lazy {
        context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tan, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView()
    }

    private fun initWebView() {
        webView?.settings?.javaScriptEnabled = true
        webView2?.settings?.javaScriptEnabled = true
        webView?.addJavascriptInterface(InJavaScriptLocalObj(), "java_obj")
        webView2?.addJavascriptInterface(InJavaScriptLocalObj2(), "java_obj")
        webView?.webViewClient = CWebViewClient()
        webView2?.webViewClient = CWebViewClient2()
        MClass.tanCompleteListener = object : TanCompleteListener {
            override fun onTanCompleteListener(mDataList: List<MainBean>) {
                view?.post {
                    saveData(mDataList)
                }
            }
        }

        view?.findViewById<Button>(R.id.freshButton)?.onClick {
            view?.findViewById<TextView>(R.id.titleText)?.text = "正在加载数据......"
            webView?.loadUrl(DEFAULT_WEB_URL)
        }

        view?.findViewById<Button>(R.id.validateButton)?.onClick {
            view?.findViewById<TextView>(R.id.titleText)?.text = "正在加载数据......"
            val date = sharedPreferences.getString(SP_DATE_KEY, "")
            val sb = StringBuffer()
            sb.append(date.substring(0, date.indexOf("-")))
            sb.append(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")))
            sb.append(date.substring(date.lastIndexOf("-") + 1))
            val url = DEFAULT_VALIDATE_URL + sb.toString() + ".htm"
            Log.i("########", "url:" + url)
            webView2?.loadUrl(url)
        }

        parseData()
    }

    private fun saveData(mDataList: List<MainBean>) {
        Log.i(TAG, "数据解析完成.........")
        val date = sharedPreferences.getString(SP_TIME_KEY, "")
        if (!TextUtils.isEmpty(date) && date != getYMD()) {
            Log.i(TAG, "清空历史数据......")
            sharedPreferences.edit().clear().apply()
        }
        Log.i(TAG,"保存本次数据....... 比赛数量：" + mDataList.size)
        sharedPreferences.edit().putString(SP_TIME_KEY, getYMDHMS()).apply()
        sharedPreferences.edit().putString(SP_DATE_KEY, getYMD()).apply()
        sharedPreferences.edit().putString(SP_DATA_KEY, Gson().toJson(mDataList)).apply()

        parseData()
    }

    private fun parseData() {
        val date = sharedPreferences.getString(SP_DATE_KEY, "")
        if (!TextUtils.isEmpty(date)) {
            val data = sharedPreferences.getString(SP_DATA_KEY, "{}")
            val mDataList = Gson().fromJson<List<MainBean>>(data, object : TypeToken<List<MainBean>>() {}.type)
            view?.findViewById<TextView>(R.id.titleText)?.text = "今日有效比赛数据：" +
                    mDataList.size + "场\n刷新时间：" + sharedPreferences.getString(SP_TIME_KEY, "")

            fillItem(MClass.parse144(mDataList) as ArrayList<MainBean>, R.id.oneLayout)
            fillItem(MClass.parse165(mDataList) as ArrayList<MainBean>, R.id.one65Layout)
            fillItem(MClass.parseCOver(mDataList) as ArrayList<MainBean>, R.id.coverLayout)
            fillItem(MClass.parseDown(mDataList) as ArrayList<MainBean>, R.id.downLayout)
            fillItem(MClass.parseZero(mDataList) as ArrayList<MainBean>, R.id.zeroLayout)
            fillItem(MClass.parseCut(mDataList) as ArrayList<MainBean>, R.id.cutLayout)
        }
    }

    private fun fillItem(mList: ArrayList<MainBean>, parentId: Int) {
        view?.findViewById<LinearLayout>(parentId)?.removeAllViews()
        for (i in 0 until mList.size) {
            val rootViews = LayoutInflater.from(context).inflate(R.layout.fragment_parser_item, null, false)
            rootViews.findViewById<TextView>(R.id.number).text = changeNumIndex(i + 1)
            rootViews.findViewById<TextView>(R.id.bisai).text = mList[i].liansai
            rootViews.findViewById<TextView>(R.id.time).text = mList[i].time
            rootViews.findViewById<TextView>(R.id.zhudiu).text = mList[i].getZhu()
            rootViews.findViewById<TextView>(R.id.kedui).text = mList[i].getKe()
            rootViews.findViewById<TextView>(R.id.yaPanText).text = "0"
            rootViews.findViewById<TextView>(R.id.pointText).text = mList[i].getBifen()
            rootViews.findViewById<TextView>(R.id.resultText).text = mList[i].getResult()

            rootViews.findViewById<TextView>(R.id.yaText).onClick {
                startWebActivity(context, mList[i].yaUrl)
            }
            rootViews.findViewById<TextView>(R.id.ouText).onClick {
                startWebActivity(context, mList[i].ouUrl)
            }
            rootViews.findViewById<TextView>(R.id.ouText).onClick {
                startWebActivity(context, mList[i].getBigUrl())
            }
            rootViews.onClick {
                val intent = Intent("bet007.main")
                startActivity(intent)
            }
            view?.findViewById<LinearLayout>(parentId)?.addView(rootViews)
        }
    }

    class CWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            view?.postDelayed({
                try {
                    view.loadUrl("javascript:window.java_obj.showSource("
                            + "document.getElementById('table_live').outerHTML);")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, 0)

            super.onPageFinished(view, url)
        }
    }

    inner class InJavaScriptLocalObj {

        @JavascriptInterface
        fun showSource(html: String) {
            val htmlStr = DEFAULT_HTML_PRE + html + DEFAULT_HTML_LAST
            val doc = Jsoup.parse(htmlStr)
            MClass.parseMainData(doc)
        }
    }

    class CWebViewClient2 : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            view?.postDelayed({
                try {
                    view.loadUrl("javascript:window.java_obj.showSource("
                            + "document.getElementById('table_live').outerHTML);")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, 0)

            super.onPageFinished(view, url)
        }
    }

    inner class InJavaScriptLocalObj2 {

        @JavascriptInterface
        fun showSource(html: String) {
            val htmlStr = DEFAULT_HTML_PRE + html + DEFAULT_HTML_LAST
            val doc = Jsoup.parse(htmlStr)
            val mList = MClass.parseResult(doc)

            val data = sharedPreferences.getString(SP_DATA_KEY, "{}")
            val mDataList = Gson().fromJson<List<MainBean>>(data, object : TypeToken<List<MainBean>>() {}.type)
            val mList1 = MClass.parse144(mDataList)
            for (i in 0 until mList1.size) {
                for (j in 0 until mList.size) {
                    if (mList[j].getZhudui().contains(mList1[i].getZhu())) {
                        mList1[i].bifen = mList[j].points
                        break
                    }
                }
            }

            context.runOnUiThread {
                fillItem(mList1 as ArrayList<MainBean>, R.id.oneLayout)
            }
        }
    }
}


fun createTanFragment(): TanFragment {
    return TanFragment()
}

interface TanListener {
    fun onTanListener()
}

interface TanCompleteListener {
    fun onTanCompleteListener(mDataList: List<MainBean>)
}