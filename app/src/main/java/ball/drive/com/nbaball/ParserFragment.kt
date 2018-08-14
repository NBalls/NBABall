package ball.drive.com.nbaball

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import ball.drive.com.nbaball.DetailActivity.Companion.startDetailActivity
import ball.drive.com.nbaball.WebActivity.Companion.startWebActivity
import ball.drive.com.nbaball.qdog.ParseData
import ball.drive.com.nbaball.qdog.bean.MBean
import ball.drive.com.nbaball.utils.getYMD
import ball.drive.com.nbaball.utils.getYMDHMS
import com.google.gson.Gson
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.onClick
import org.jetbrains.anko.runOnUiThread
import kotlin.collections.ArrayList


/**
 * Created by aaron on 2018/4/11.
 */
class ParserFragment: BaseFragment() {

    companion object {
        val SHARE_NAME = "parser_data"
        val SP_TIME_KEY = "sp_time_key"
        val SP_DATE_KEY = "sp_date_key"
        val SP_DATA_KEY = "sp_data_key"
    }

    private val TAG = "ParserFragment"
    private var mainList = ArrayList<MBean>()
    private val sharedPreferences by lazy {
        context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_parser, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Observable.interval(0, 10 * 60 * 1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i(TAG,"###### print interval data......")
                    printData()
                }, {})

        ParseData.onParseListener = object : OnParseListener {
            override fun onParseRate(index: Int, isNormal: Boolean) {
                context.runOnUiThread {
                    if (isNormal) {
                        view?.findViewById<TextView>(R.id.parseStatus)?.text = "开始解析第 " + index + " 条数据......"
                    } else {
                        view?.findViewById<TextView>(R.id.parseStatus)?.text = "" + index + " 条数据为无效数据不在解析......"
                    }
                }
            }

        }

        view?.findViewById<Button>(R.id.freshButton)?.onClick {
            parserData()
        }
    }

    private fun parserData() {
        Observable.create<Boolean> { subscribe ->
            mainList = ParseData.parseMainData() as ArrayList<MBean>
            Log.i(TAG, "###### 解析数据成功......")
            val date = sharedPreferences.getString(SP_TIME_KEY, "")
            if (!TextUtils.isEmpty(date) && date != getYMD()) {
                Log.i(TAG, "###### 清空历史数据......")
                sharedPreferences.edit().clear().commit()
            }
            Log.i(TAG,"###### 保存本次数据.......")
            sharedPreferences.edit().putString(SP_TIME_KEY, getYMDHMS()).commit()
            sharedPreferences.edit().putString(SP_DATE_KEY, getYMD()).commit()
            sharedPreferences.edit().putString(SP_DATA_KEY, Gson().toJson(mainList)).commit()
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
            print144(mainList)
            printDown(mainList)
            print275(mainList)
            print075(mainList)
            print2Over(mainList)
            printCOver(mainList)
            view?.findViewById<TextView>(R.id.parseStatus)?.text = "未在解析数据"
        }
    }

    private fun printTitle(mainList: ArrayList<MBean>) {
        val sb = StringBuffer("今天目前为止有效比赛：")
        sb.append(mainList.size)
        sb.append("\n刷新时间：")
        sb.append(sharedPreferences.getString(SP_TIME_KEY, ""))
        view?.findViewById<TextView>(R.id.parserTitle)?.text = sb.toString()
    }

    private fun print144(mainList: ArrayList<MBean>) {
        val mList = ParseData.print144(mainList)
        view?.findViewById<TextView>(R.id.oneTitle)?.text = "今日144比赛" + mList.size + "场"
        fillItem(mList as ArrayList<MBean>, R.id.oneLayout)
    }

    private fun printDown(mainList: ArrayList<MBean>) {
        val mList = ParseData.printDown(mainList)
        view?.findViewById<TextView>(R.id.downTitle)?.text = "今日下盘低水比赛" + mList.size + "场"
        fillItem(mList as ArrayList<MBean>, R.id.downLayout)
    }

    private fun print275(mainList: ArrayList<MBean>) {
        val mList = ParseData.print275(mainList)
        view?.findViewById<TextView>(R.id.bigTitle)?.text = "今日大2.75比赛" + mList.size + "场"
        fillItem(mList as ArrayList<MBean>, R.id.bigLayout)
    }

    private fun print075(mainList: ArrayList<MBean>) {
        val mList = ParseData.print075(mainList)
        view?.findViewById<TextView>(R.id.banTitle)?.text = "今日半一盘比赛" + mList.size + "场"
        fillItem(mList as ArrayList<MBean>, R.id.banLayout)
    }

    private fun print2Over(mainList: ArrayList<MBean>) {
        val mList = ParseData.print2Over(mainList)
        view?.findViewById<TextView>(R.id.overTitle)?.text = "今日连续变盘比赛" + mList.size + "场"
        fillItem(mList as ArrayList<MBean>, R.id.overLayout)
    }

    private fun printCOver(mainList: ArrayList<MBean>) {
        val mList = ParseData.printCOver(mainList)
        view?.findViewById<TextView>(R.id.coverTitle)?.text = "今日与近期差距较大比赛" + mList.size + "场"
        fillItem(mList as ArrayList<MBean>, R.id.coverLayout)
    }

    private fun fillItem(mList: ArrayList<MBean>, parentId: Int) {
        for (i in 0 until mList.size) {
            val rootViews = LayoutInflater.from(context).inflate(R.layout.fragment_parser_item, null, false)
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
}

interface OnParseListener {
    fun onParseRate(index: Int, isNormal: Boolean)
}

fun createParserFragment(): ParserFragment {
    return ParserFragment()
}