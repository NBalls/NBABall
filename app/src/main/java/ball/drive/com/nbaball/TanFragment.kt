package ball.drive.com.nbaball

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.JavascriptInterface
import android.widget.*
import ball.drive.com.nbaball.qiutan.MClass
import ball.drive.com.nbaball.qiutan.MainBean
import ball.drive.com.nbaball.qiutan.PrintClass
import ball.drive.com.nbaball.qiutan.RBean
import ball.drive.com.nbaball.utils.getYMD
import ball.drive.com.nbaball.utils.getYMDHMS
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.onClick
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.textColor
import org.jsoup.Jsoup
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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
        val DEFAULT_WEB_URL = "http://www.win0168.com"
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
        initLayout()
        initWebView()
    }

    private fun initLayout() {
        if (BuildConfig.ISSHOW_ONE_LAYOUT) {
            view?.findViewById<LinearLayout>(R.id.outOneLayout)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<LinearLayout>(R.id.outOneLayout)?.visibility = View.GONE
        }
        if (BuildConfig.ISSHOW_ONE65_LAYOUT) {
            view?.findViewById<LinearLayout>(R.id.outOne65Layout)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<LinearLayout>(R.id.outOne65Layout)?.visibility = View.GONE
        }
        if (BuildConfig.ISSHOW_COVER_LAYOUT) {
            view?.findViewById<LinearLayout>(R.id.outCoverLayout)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<LinearLayout>(R.id.outCoverLayout)?.visibility = View.GONE
        }
        if (BuildConfig.ISSHOW_DOWN_LAYOUT) {
            view?.findViewById<LinearLayout>(R.id.outDownLayout)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<LinearLayout>(R.id.outDownLayout)?.visibility = View.GONE
        }
        if (BuildConfig.ISSHOW_ZERO_LAYOUT) {
            view?.findViewById<LinearLayout>(R.id.outZeroLayout)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<LinearLayout>(R.id.outZeroLayout)?.visibility = View.VISIBLE
        }
        if (BuildConfig.ISSHOW_CUT_LAYOUT) {
            view?.findViewById<LinearLayout>(R.id.outCutLayout)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<LinearLayout>(R.id.outCutLayout)?.visibility = View.GONE
        }
        if (BuildConfig.ISSHOW_ONE25_LAYOUT) {
            view?.findViewById<LinearLayout>(R.id.outOne25Layout)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<LinearLayout>(R.id.outOne25Layout)?.visibility = View.GONE
        }
        if (BuildConfig.ISSHOW_DEEP_LAYOUT) {
            view?.findViewById<LinearLayout>(R.id.outDeepLayout)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<LinearLayout>(R.id.outDeepLayout)?.visibility = View.GONE
        }
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
            val sb = StringBuffer()
            val date = sharedPreferences.getString(SP_DATE_KEY, "")
            sb.append(date.substring(0, date.indexOf("-")))
            sb.append(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")))
            sb.append(date.substring(date.lastIndexOf("-") + 1))
            val sdf = SimpleDateFormat("yyyyMMdd")
            if (sb.toString() != sdf.format(Date())) {
                view?.findViewById<Button>(R.id.validateButton)?.text = "验证中..."
                val url = DEFAULT_VALIDATE_URL + sb.toString() + ".htm"
                webView2?.loadUrl(url)
            } else {
                Toast.makeText(context, "数据无法验证.....", Toast.LENGTH_SHORT).show()
            }
        }

        view?.findViewById<Button>(R.id.saveButton)?.onClick {
            val date = sharedPreferences.getString(SP_DATE_KEY, "")
            val data = sharedPreferences.getString(SP_DATA_KEY, "{}")
            val time = sharedPreferences.getString(SP_TIME_KEY, "")
            sharedPreferences.edit().putString(date, data).apply()
            sharedPreferences.edit().putString(date + "_time", time)
            Toast.makeText(context, "保存数据成功......", Toast.LENGTH_SHORT).show()
        }

        view?.findViewById<Button>(R.id.queryButton)?.onClick {
            view?.findViewById<LinearLayout>(R.id.dateLayout)?.visibility = View.VISIBLE
        }

        view?.findViewById<Button>(R.id.goneButton)?.onClick {
            view?.findViewById<LinearLayout>(R.id.dateLayout)?.visibility = View.GONE
        }

        view?.findViewById<Button>(R.id.sureButton)?.onClick {
            val date = view?.findViewById<EditText>(R.id.dateEdit)?.text.toString()
            if (TextUtils.isEmpty(date) || !date.matches(Regex("\\d\\d\\d\\d-\\d\\d-\\d\\d"))) {
                Toast.makeText(context, "日期格式不正确....", Toast.LENGTH_SHORT).show()
            } else {
                view?.findViewById<LinearLayout>(R.id.dateLayout)?.visibility = View.GONE
                val data = sharedPreferences.getString(date, "{}")
                val dateTime = sharedPreferences.getString(date + "_time", "")
                val time = dateTime.substring(dateTime.indexOf(" ") + 1)
                parseData(data, date, time)
            }
        }

        val data = sharedPreferences.getString(SP_DATA_KEY, "[]")
        val date = sharedPreferences.getString(SP_DATE_KEY, "")
        val dateTime = sharedPreferences.getString(SP_TIME_KEY, "")
        val time = dateTime.substring(dateTime.indexOf(" ") + 1)
        parseData(data, date, time)
    }

    private fun saveData(mDataList: List<MainBean>) {
        Log.i(TAG,"保存本次数据....... 比赛数量：" + mDataList.size)
        sharedPreferences.edit().putString(SP_DATA_KEY, "[]").apply()
        sharedPreferences.edit().putString(SP_TIME_KEY, getYMDHMS()).apply()
        sharedPreferences.edit().putString(SP_DATE_KEY, getYMD()).apply()
        sharedPreferences.edit().putString(SP_DATA_KEY, Gson().toJson(mDataList)).apply()

        val dateTime = sharedPreferences.getString(SP_TIME_KEY, "")
        val time = dateTime.substring(dateTime.indexOf(" ") + 1)
        val date = sharedPreferences.getString(SP_DATE_KEY, "")
        parseData(sharedPreferences.getString(SP_DATA_KEY, "{}"), date, time)
    }

    private fun parseData(data: String, date: String, time: String) {
        val mDataList = Gson().fromJson<List<MainBean>>(data, object : TypeToken<List<MainBean>>() {}.type)
        val sb = StringBuffer()
        sb.append("有效比赛:").append(mDataList.size).append("场\n")
        sb.append("刷新日期:").append(date).append("\n")
        sb.append("刷新时间:").append(time).append("\n")
        view?.findViewById<TextView>(R.id.titleText)?.text = sb.toString()

        fillItem(PrintClass.parse144(mDataList) as ArrayList<MainBean>, R.id.oneLayout)
        fillItem(PrintClass.parse165(mDataList) as ArrayList<MainBean>, R.id.one65Layout)
        fillItem(PrintClass.parseCOver(mDataList) as ArrayList<MainBean>, R.id.coverLayout)
        fillItem(PrintClass.parseDown(mDataList) as ArrayList<MainBean>, R.id.downLayout)
        fillItem(PrintClass.parseZero(mDataList) as ArrayList<MainBean>, R.id.zeroLayout)
        fillItem(PrintClass.parseCut(mDataList) as ArrayList<MainBean>, R.id.cutLayout)
        fillItem(PrintClass.parse025(mDataList) as ArrayList<MainBean>, R.id.one25Layout)
        fillItem(PrintClass.parseDeep(mDataList) as ArrayList<MainBean>, R.id.deepLayout)
    }

    private fun fillItem(mList: ArrayList<MainBean>, parentId: Int) {
        view?.findViewById<LinearLayout>(parentId)?.removeAllViews()
        for (i in 0 until mList.size) {
            val rootViews = LayoutInflater.from(context).inflate(R.layout.fragment_parser_item, null, false)
            rootViews.findViewById<TextView>(R.id.bisai).text = mList[i].liansai
            rootViews.findViewById<TextView>(R.id.time).text = mList[i].time
            rootViews.findViewById<TextView>(R.id.zhudiu).text = mList[i].getZhu()
            rootViews.findViewById<TextView>(R.id.kedui).text = mList[i].getKe()
            rootViews.findViewById<TextView>(R.id.panText).text = getEndPan(mList[i])
            rootViews.findViewById<TextView>(R.id.resultText).text = mList[i].bifen
            val bifen = mList[i].bifen
            if (bifen.matches(Regex("\\d-\\d"))) {
                val zPoint = bifen.substring(0, 1)
                val kPoint = bifen.substring(2, 3)
                if (zPoint.toInt() > kPoint.toInt()) {
                    rootViews.findViewById<TextView>(R.id.rText).text = "赢"
                    rootViews.findViewById<TextView>(R.id.rText).textColor = Color.parseColor("#FF0000")
                } else if (zPoint.toInt() < kPoint.toInt()) {
                    rootViews.findViewById<TextView>(R.id.rText).text = "输"
                    rootViews.findViewById<TextView>(R.id.rText).textColor = Color.parseColor("#00FF00")
                } else {
                    rootViews.findViewById<TextView>(R.id.rText).text = "平"
                    rootViews.findViewById<TextView>(R.id.rText).textColor = Color.parseColor("#000000")
                }
            } else {
                rootViews.findViewById<TextView>(R.id.rText).text = "未"
                rootViews.findViewById<TextView>(R.id.rText).textColor = Color.parseColor("#000000")
            }
            parseYa(rootViews.findViewById(R.id.parseYaLayout), mList[i])

            rootViews.findViewById<TextView>(R.id.yaText).onClick {
                val layout = rootViews.findViewById<LinearLayout>(R.id.parseYaLayout)
                if (layout.visibility == View.VISIBLE) {
                    layout.visibility = View.GONE
                } else {
                    layout.visibility = View.VISIBLE
                }
            }

            rootViews.findViewById<LinearLayout>(R.id.liansaiLayout).onClick {
                val intent = Intent(context, TDetailActivity::class.java)
                intent.putExtra("mainBean", mList[i])
                context.startActivity(intent)
            }

            rootViews.findViewById<TextView>(R.id.analyseText).onClick {
                Toast.makeText(context, "打开分析数据......", Toast.LENGTH_SHORT).show()
            }

            rootViews.onClick {
                // val intent = Intent("bet007.main")
                // startActivity(intent)
            }
            val lps = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            view?.findViewById<LinearLayout>(parentId)?.addView(rootViews, lps)
        }
    }

    private fun parseYa(container: LinearLayout, mainBean: MainBean) {
        container.addView(LayoutInflater.from(context).inflate(R.layout.fragment_parser_item_ya_title, null, false))
        for (i in 0 until mainBean.yList.size) {
            if (isFilter(mainBean.yList[i].company)) {
                val rootViews = LayoutInflater.from(context).inflate(R.layout.fragment_parser_item_ya, null, false)
                rootViews.findViewById<TextView>(R.id.yCompany).text = mainBean.yList[i].company.trim()
                rootViews.findViewById<TextView>(R.id.startZRate).text = mainBean.yList[i].startZRate.trim()
                rootViews.findViewById<TextView>(R.id.startPan).text = mainBean.yList[i].startPan.trim()
                rootViews.findViewById<TextView>(R.id.startKRate).text = mainBean.yList[i].startKRate.trim()

                rootViews.findViewById<TextView>(R.id.endZRate).text = mainBean.yList[i].endZRate.trim()
                if (mainBean.yList[i].endZRate.toFloat() > mainBean.yList[i].startZRate.toFloat()) {
                    rootViews.findViewById<TextView>(R.id.endZRate).textColor = Color.parseColor("#FF0000")
                } else if (mainBean.yList[i].endZRate.toFloat() < mainBean.yList[i].startZRate.toFloat()) {
                    rootViews.findViewById<TextView>(R.id.endZRate).textColor = Color.parseColor("#00FF00")
                } else {
                    rootViews.findViewById<TextView>(R.id.endZRate).textColor = Color.parseColor("#000000")
                }
                rootViews.findViewById<TextView>(R.id.endPan).text = mainBean.yList[i].endPan.trim()
                if (mainBean.yList[i].endPan.toFloat() > mainBean.yList[i].startPan.toFloat()) {
                    rootViews.findViewById<TextView>(R.id.endPan).textColor = Color.parseColor("#FF0000")
                } else if (mainBean.yList[i].endPan.toFloat() < mainBean.yList[i].startPan.toFloat()) {
                    rootViews.findViewById<TextView>(R.id.endPan).textColor = Color.parseColor("#00FF00")
                } else {
                    rootViews.findViewById<TextView>(R.id.endPan).textColor = Color.parseColor("#000000")
                }
                rootViews.findViewById<TextView>(R.id.endKRate).text = mainBean.yList[i].endKRate.trim()
                if (mainBean.yList[i].endKRate.toFloat() > mainBean.yList[i].startKRate.toFloat()) {
                    rootViews.findViewById<TextView>(R.id.endKRate).textColor = Color.parseColor("#FF0000")
                } else if (mainBean.yList[i].endKRate.toFloat() < mainBean.yList[i].startKRate.toFloat()) {
                    rootViews.findViewById<TextView>(R.id.endKRate).textColor = Color.parseColor("#00FF00")
                } else {
                    rootViews.findViewById<TextView>(R.id.endKRate).textColor = Color.parseColor("#000000")
                }
                container.addView(rootViews)
            }
        }
        container.addView(LayoutInflater.from(context).inflate(R.layout.fragment_parser_item_ou_title, null, false))
        for (i in 0 until mainBean.oList.size) {
            if (isFilter(mainBean.oList[i].company)) {
                val rootViews = LayoutInflater.from(context).inflate(R.layout.fragment_parser_item_ou, null, false)
                rootViews.findViewById<TextView>(R.id.oCompany).text = mainBean.oList[i].company
                rootViews.findViewById<TextView>(R.id.startSRate).text = mainBean.oList[i].startS
                rootViews.findViewById<TextView>(R.id.endSRate).text = mainBean.oList[i].endS
                if (mainBean.oList[i].endS.trim().toFloat() > mainBean.oList[i].startS.trim().toFloat()) {
                    rootViews.findViewById<TextView>(R.id.endSRate).textColor = Color.parseColor("#FF0000")
                } else if (mainBean.oList[i].endS.trim().toFloat() < mainBean.oList[i].startS.trim().toFloat()) {
                    rootViews.findViewById<TextView>(R.id.endSRate).textColor = Color.parseColor("#00FF00")
                } else {
                    rootViews.findViewById<TextView>(R.id.endSRate).textColor = Color.parseColor("#000000")
                }
                rootViews.findViewById<TextView>(R.id.startPRate).text = mainBean.oList[i].startP
                rootViews.findViewById<TextView>(R.id.endPRate).text = mainBean.oList[i].endP
                if (mainBean.oList[i].endP.toFloat() > mainBean.oList[i].startP.toFloat()) {
                    rootViews.findViewById<TextView>(R.id.endPRate).textColor = Color.parseColor("#FF0000")
                } else if (mainBean.oList[i].endP.toFloat() < mainBean.oList[i].startP.toFloat()) {
                    rootViews.findViewById<TextView>(R.id.endPRate).textColor = Color.parseColor("#00FF00")
                } else {
                    rootViews.findViewById<TextView>(R.id.endPRate).textColor = Color.parseColor("#000000")
                }
                rootViews.findViewById<TextView>(R.id.startFRate).text = mainBean.oList[i].startF
                rootViews.findViewById<TextView>(R.id.endFRate).text = mainBean.oList[i].endF
                if (mainBean.oList[i].endF.toFloat() > mainBean.oList[i].startF.toFloat()) {
                    rootViews.findViewById<TextView>(R.id.endFRate).textColor = Color.parseColor("#FF0000")
                } else if (mainBean.oList[i].endF.toFloat() < mainBean.oList[i].startF.toFloat()) {
                    rootViews.findViewById<TextView>(R.id.endFRate).textColor = Color.parseColor("#00FF00")
                } else {
                    rootViews.findViewById<TextView>(R.id.endFRate).textColor = Color.parseColor("#000000")
                }
                container.addView(rootViews)
            }
        }
    }

    private fun isFilter(company: String): Boolean {
        return company.contains("365") ||
                company.contains("易胜博") ||
                company.contains("Crown") ||
                company.contains("威廉") ||
                company.contains("韦德") ||
                company.contains("澳门") ||
                company.contains("金宝博") ||
                company.contains("盈禾") ||
                company.contains("立博")

    }

    private fun getEndPan(mainBean: MainBean): String {
        for (i in 0 until mainBean.yList.size) {
            if (mainBean.yList[i].company.contains("365")) {
                return mainBean.yList[i].endPan
            }
        }

        return ""
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
            Observable.create<List<RBean>> { subscribe ->
                val htmlStr = DEFAULT_HTML_PRE + html + DEFAULT_HTML_LAST
                val doc = Jsoup.parse(htmlStr)
                val mList = MClass.parseResult(doc)
                subscribe.onNext(mList)
                subscribe.onCompleted()
            }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ mList ->
                        validateData(mList)
                    }, {})
        }
    }

    private fun validateData(mList: List<RBean>) {
        val data = sharedPreferences.getString(SP_DATA_KEY, "{}")
        val mDataList = Gson().fromJson<List<MainBean>>(data, object : TypeToken<List<MainBean>>() {}.type)
        for (i in 0 until mDataList.size) {
            for (j in 0 until mList.size) {
                if (mList[j].getZhudui().contains(mDataList[i].getZhu())
                        && mList[j].getLiansai().contains(mDataList[i].liansai)) {
                    mDataList[i].bifen = mList[j].points
                    break
                }
            }
        }

        context.runOnUiThread {
            // 更新保存数据
            view?.findViewById<Button>(R.id.validateButton)?.text = "验证"
            sharedPreferences.edit().putString(SP_DATA_KEY, Gson().toJson(mDataList)).apply()
            fillItem(PrintClass.parse144(mDataList) as ArrayList<MainBean>, R.id.oneLayout)
            fillItem(PrintClass.parse165(mDataList) as ArrayList<MainBean>, R.id.one65Layout)
            fillItem(PrintClass.parseCOver(mDataList) as ArrayList<MainBean>, R.id.coverLayout)
            fillItem(PrintClass.parseDown(mDataList) as ArrayList<MainBean>, R.id.downLayout)
            fillItem(PrintClass.parseZero(mDataList) as ArrayList<MainBean>, R.id.zeroLayout)
            fillItem(PrintClass.parseCut(mDataList) as ArrayList<MainBean>, R.id.cutLayout)
            fillItem(PrintClass.parse025(mDataList) as ArrayList<MainBean>, R.id.one25Layout)
            fillItem(PrintClass.parseDeep(mDataList) as ArrayList<MainBean>, R.id.deepLayout)
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