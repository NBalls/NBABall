package ball.drive.com.nbaball

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ball.drive.com.nbaball.DetailActivity.Companion.startDetailActivity
import ball.drive.com.nbaball.WebActivity.Companion.startWebActivity
import ball.drive.com.nbaball.qdog.ParseData
import ball.drive.com.nbaball.qdog.bean.MBean
import ball.drive.com.nbaball.utils.changeNumIndex
import ball.drive.com.nbaball.utils.getYMD
import ball.drive.com.nbaball.utils.getYMDHMS
import com.google.gson.Gson
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.onClick
import org.jetbrains.anko.runOnUiThread
import kotlin.collections.ArrayList


/**
 * Created by aaron on 2018/4/11.
 * 解析数据Fragment
 */
class ParserFragment: BaseFragment() {

    companion object {
        val SHARE_NAME = "parser_data"
        val SP_TIME_KEY = "sp_time_key"
        val SP_DATE_KEY = "sp_date_key"
        val SP_DATA_KEY = "sp_data_key"

        val NORMAL_START_PRE = "开始解析第 "
        val NORMAL_START_LAST = " 条数据......"
        val ERROR_START_LAST = " 条数据为无效数据不在解析......"

        val DEFAULT_STATUS = "未在解析数据"
        val TITLE = "近日有效比赛："
        val VALID = "刷新时间："
        val MEASURE = "场"
        val TODAY_144 = "今日144比赛"
        val TODAY_DOWN = "今日下盘低水比赛"
        val TODAY_275 = "今日大2.75比赛"
        val TODAY_075 = "今日半一比赛"
        val TODAY_OVER = "今日变盘比赛"
        val TODAY_COVER = "今日差距较大比赛"
        val TODAY_BAN = "今日平半比赛"
        val TODAY_ZERO = "今日平手比赛"
    }

    private val DEFAULT_URL = "http://live.titan007.com"
    private val TAG = "ParserFragment"
    private val arr = ArrayList<Integer>()
    private var mainList = ArrayList<MBean>()
    private val sharedPreferences by lazy {
        context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_parser, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ParseData.onParseListener = object : OnParseListener {
            override fun onParseRate(index: Int, isNormal: Boolean) {
                context.runOnUiThread {
                    if (isNormal) {
                        val text = NORMAL_START_PRE + index / 2 + NORMAL_START_LAST
                        view?.findViewById<TextView>(R.id.parseStatus)?.text = text
                    } else {
                        val text = (index / 2).toString() + ERROR_START_LAST
                        view?.findViewById<TextView>(R.id.parseStatus)?.text = text
                    }
                }
            }
        }

        view?.findViewById<Button>(R.id.freshButton)?.onClick {
            parserData(arr[view.findViewById<Spinner>(R.id.spinner).selectedItemPosition].toInt())
        }

        initSpinner()
        printData()
    }

    private fun parserData(count: Int) {
        Observable.create<Boolean> { subscribe ->
            mainList = ParseData.parseMainData(count) as ArrayList<MBean>
            Log.i(TAG, "###### 解析数据成功......")
            val date = sharedPreferences.getString(SP_TIME_KEY, "")
            if (!TextUtils.isEmpty(date) && date != getYMD()) {
                Log.i(TAG, "###### 清空历史数据......")
                sharedPreferences.edit().clear().apply()
            }
            Log.i(TAG,"###### 保存本次数据....... 比赛数量：" + mainList.size)
            sharedPreferences.edit().putString(SP_TIME_KEY, getYMDHMS()).apply()
            sharedPreferences.edit().putString(SP_DATE_KEY, getYMD()).apply()
            sharedPreferences.edit().putString(SP_DATA_KEY, Gson().toJson(mainList)).apply()
            subscribe.onNext(true)
            subscribe.onCompleted()
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            printData()
        }, {})
    }

    private fun printData() {
        Log.i(TAG, "###### 从sp中获取数据并解析......")
        val date = sharedPreferences.getString(SP_DATE_KEY, "")
        if (!TextUtils.isEmpty(date) && date == getYMD()) {
            Log.i(TAG,"###### 解析显示本日数据......")
            val data = sharedPreferences.getString(SP_DATA_KEY, "{}")
            mainList = Gson().fromJson(data, object : TypeToken<List<MBean>>() {}.type)
            printTitle(mainList)
            printDown(mainList)
            print2Over(mainList)
            printCOver(mainList)
            printZero(mainList)
            printPB(mainList)
            print144(mainList)
            print275(mainList)
            print075(mainList)
            printAll(mainList)
            view?.findViewById<TextView>(R.id.parseStatus)?.text = DEFAULT_STATUS
        }
    }

    private fun printTitle(mainList: ArrayList<MBean>) {
        val sb = StringBuffer(TITLE)
        sb.append(mainList.size).append("\n").append(VALID)
        sb.append(sharedPreferences.getString(SP_TIME_KEY, ""))
        view?.findViewById<TextView>(R.id.parserTitle)?.text = sb.toString()
    }

    private fun printZero(mainList: ArrayList<MBean>) {
        val mList = ParseData.printZero(mainList)
        view?.findViewById<TextView>(R.id.zeroTitle)?.text = TODAY_ZERO + mList.size + MEASURE
        fillItem(mList as ArrayList<MBean>, R.id.zeroLayout)
    }

    private fun printPB(mainList: ArrayList<MBean>) {
        val mList = ParseData.printBan(mainList)
        view?.findViewById<TextView>(R.id.pbTitle)?.text = TODAY_BAN + mList.size + MEASURE
        fillItem(mList as ArrayList<MBean>, R.id.pbLayout)
    }

    private fun print144(mainList: ArrayList<MBean>) {
        val mList = ParseData.print144(mainList)
        view?.findViewById<TextView>(R.id.oneTitle)?.text = TODAY_144 + mList.size + MEASURE
        fillItem(mList as ArrayList<MBean>, R.id.oneLayout)
    }

    private fun printDown(mainList: ArrayList<MBean>) {
        val mList = ParseData.printDown(mainList)
        view?.findViewById<TextView>(R.id.downTitle)?.text = TODAY_DOWN + mList.size + MEASURE
        fillItem(mList as ArrayList<MBean>, R.id.downLayout)
    }

    private fun print275(mainList: ArrayList<MBean>) {
        val mList = ParseData.print275(mainList)
        view?.findViewById<TextView>(R.id.bigTitle)?.text = TODAY_275 + mList.size + MEASURE
        fillItem(mList as ArrayList<MBean>, R.id.bigLayout)
    }

    private fun print075(mainList: ArrayList<MBean>) {
        val mList = ParseData.print075(mainList)
        view?.findViewById<TextView>(R.id.banTitle)?.text = TODAY_075 + mList.size + MEASURE
        fillItem(mList as ArrayList<MBean>, R.id.banLayout)
    }

    private fun print2Over(mainList: ArrayList<MBean>) {
        val mList = ParseData.print2Over(mainList)
        view?.findViewById<TextView>(R.id.overTitle)?.text = TODAY_OVER + mList.size + MEASURE
        fillItem(mList as ArrayList<MBean>, R.id.overLayout)
    }

    private fun printCOver(mainList: ArrayList<MBean>) {
        val mList = ParseData.printCOver(mainList)
        view?.findViewById<TextView>(R.id.coverTitle)?.text = TODAY_COVER + mList.size + MEASURE
        fillItem(mList as ArrayList<MBean>, R.id.coverLayout)
    }

    private fun printAll(mainList: ArrayList<MBean>) {
        fillItem(mainList, R.id.allLayout)
    }

    private fun fillItem(mList: ArrayList<MBean>, parentId: Int) {
        for (i in 0 until mList.size) {
            val rootViews = LayoutInflater.from(context).inflate(R.layout.fragment_parser_item, null, false)
            rootViews.findViewById<TextView>(R.id.number).text = changeNumIndex(i + 1)
            rootViews.findViewById<TextView>(R.id.bisai).text = mList[i].liansai
            rootViews.findViewById<TextView>(R.id.time).text = mList[i].time
            rootViews.findViewById<TextView>(R.id.zhudiu).text = mList[i].getZudui()
            rootViews.findViewById<TextView>(R.id.kedui).text = mList[i].kedui

            rootViews.onClick {
                startDetailActivity(context, mList[i])
            }

            rootViews.findViewById<TextView>(R.id.zhudiu).onClick {
                startWebActivity(context, mList[i].getZhuUrl())
            }

            rootViews.findViewById<TextView>(R.id.kedui).onClick {
                startWebActivity(context, mList[i].getKeUrl())
            }
            view?.findViewById<LinearLayout>(parentId)?.addView(rootViews)
        }
    }

    /**
     * 初始化Spinner组件
     */
    private fun initSpinner() {
        arr.clear()
        arr.add(Integer(200))
        arr.add(Integer(300))
        arr.add(Integer(400))
        arr.add(Integer(500))
        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, arr)
        view?.findViewById<Spinner>(R.id.spinner)?.adapter = adapter
    }
}

interface OnParseListener {
    fun onParseRate(index: Int, isNormal: Boolean)
}

fun createParserFragment(): ParserFragment {
    return ParserFragment()
}