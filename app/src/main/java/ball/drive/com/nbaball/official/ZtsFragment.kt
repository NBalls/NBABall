package ball.drive.com.nbaball.official

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ball.drive.com.nbaball.BaseFragment
import ball.drive.com.nbaball.R
import ball.drive.com.nbaball.entity.sp.Suggest
import ball.drive.com.nbaball.entity.sp.SuggestValue
import com.google.gson.Gson
import org.jetbrains.anko.onClick
import java.text.SimpleDateFormat
import java.util.*
import android.widget.ArrayAdapter
import ball.drive.com.nbaball.utils.getYMD
import ball.drive.com.nbaball.utils.getYMDs


/**
 * Created by aaron on 2018/4/11.
 */
class ZtsFragment: BaseFragment() {

    private var temp1: String = "\uD83C\uDF89再次爆红！[奸笑]侦探号所向无敌[机智]昨天足球公推再次击中，精选双红，重心止步四连红，篮球致富单依旧还是红[耶]总体5中4！近6日更是23中17！仅6日盈利1.5A\uD83D\uDE31[红包]侦探出击，所向披靡！[發]\uD83D\uDE0F\n\n"
    private var temp2: String = "今日侦探出击赞赏推荐[嘿哈]\n\uD83D\uDC4D足球致富单：188不红退款等你约[玫瑰]\n[耶]精选足球两场：99祝你久久长红[爱心]\n[太阳]打包致富单精选单：166愿你永远发发发[西瓜]\n\n"
    private var temp3: String = "[爱心]篮球致富单：180不红包退，就是这么牛掰[白眼]\n\n"
    private var temp4: String = "\uD83C\uDFC0今日篮球包月1699，每日2-3场\uD83D\uDCAA；\n⚽足球今日包月只要1599[机智]，每日3-4场\uD83C\uDFA4；\n\n"
    private var temp5: String = "    足篮同包月更是折上折，今日只要2999[阴险]，只要2999，即可享受侦探专家每天三场足球，三场篮球。还不抓紧报名！！\uD83D\uDC2C"

    private var temp21: String = "\uD83C\uDF89再次爆红！[奸笑]侦探号所向无敌[机智]昨天足球公推再次击中，精选双红，重心止步四连红，篮球致富单依旧还是红[耶]总体5中4！近6日更是23中17！仅6日盈利1.5A\uD83D\uDE31[红包]侦探出击，所向披靡！[發]\uD83D\uDE0F\n\n"
    private var temp22: String = "\uD83C\uDFC0今日篮球包月1699,⚽足球包月1599。通包只需2999\uD83C\uDF81"
    private lateinit var sp: SharedSpUtils

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_zts, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = SharedSpUtils(context)
        view?.findViewById<Spinner>(R.id.dateSpinner)?.adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getYMDs())
        // view?.findViewById<EditText>(R.id.contentEdit)?.setText(StringBuffer().append(temp1).append(temp2).append(temp3).append(temp4).append(temp5).toString())
        // view?.findViewById<EditText>(R.id.contentEdit2)?.setText(StringBuffer().append(temp21).append(temp22).toString())

        view?.findViewById<Button>(R.id.submitButton)?.onClick {
            val suggest1 = view.findViewById<Spinner>(R.id.suggest1)!!.selectedItemPosition
            val suggest2 = view.findViewById<Spinner>(R.id.suggest2)!!.selectedItemPosition
            val suggest3 = view.findViewById<Spinner>(R.id.suggest3)!!.selectedItemPosition
            //view?.findViewById<Spinner>(R.id.dateSpinner)!!.selectedItem

            view.findViewById<TextView>(R.id.tvPublicSuggest)!!.text = parseSuggest(suggest1.toString(), SharedSpUtils.PUBLIC_SUGGEST_KEY)
            view.findViewById<TextView>(R.id.tvSpecSuggest)!!.text = parseSuggest(suggest2.toString(), SharedSpUtils.SPECTICAL_SUGGEST_KEY)
            view.findViewById<TextView>(R.id.tvCenterSuggest)!!.text = parseSuggest(suggest3.toString(), SharedSpUtils.CENTER_SUGGEST_KEY)
        }

        view?.findViewById<Button>(R.id.queryButton)?.onClick {
            view.findViewById<TextView>(R.id.tvPublicSuggest)!!.text = querySuggest(SharedSpUtils.PUBLIC_SUGGEST_KEY)
            view.findViewById<TextView>(R.id.tvSpecSuggest)!!.text = querySuggest(SharedSpUtils.SPECTICAL_SUGGEST_KEY)
            view.findViewById<TextView>(R.id.tvCenterSuggest)!!.text = querySuggest(SharedSpUtils.CENTER_SUGGEST_KEY)

            val sb = StringBuffer()

            sb.append("\uD83C\uDF89再次爆红！[奸笑]侦探号所向无敌[机智]昨天足球")
            if (parseData(SharedSpUtils.PUBLIC_SUGGEST_KEY) > 1) {
                sb.append("公推" + parseData(SharedSpUtils.PUBLIC_SUGGEST_KEY) + "连红，")
            } else if (parseData(SharedSpUtils.PUBLIC_SUGGEST_KEY) == 1) {
                sb.append("公推再次击中，")
            }
            if (parseData(SharedSpUtils.SPECTICAL_SUGGEST_KEY) > 1) {
                sb.append("精选" + parseData(SharedSpUtils.SPECTICAL_SUGGEST_KEY) + "连红，")
            } else if (parseData(SharedSpUtils.SPECTICAL_SUGGEST_KEY) == 1) {
                sb.append("精选再次击中，")
            }
            if (parseData(SharedSpUtils.CENTER_SUGGEST_KEY) > 1) {
                sb.append("重心" + parseData(SharedSpUtils.CENTER_SUGGEST_KEY) + "连红，")
            } else if (parseData(SharedSpUtils.CENTER_SUGGEST_KEY) == 1) {
                sb.append("重心再次击中，")
            }
            sb.append(temp2).append(temp3).append(temp4).append(temp5)
            view?.findViewById<EditText>(R.id.contentEdit)?.setText(sb.toString())
        }
    }

    private fun parseData(spKey: String): Int {
        val mList = querySuggestList(spKey)
        var count = 0
        for (i in 0 until mList.size) {
            if (mList[mList.size - 1 - i].value.toInt() >= 1) {
                count ++
            } else {
                break
            }
        }

        return count
    }

    private fun parseSuggest(value: String, spKey: String): String {
        val sValue = Gson().fromJson(sp.getValue(spKey), SuggestValue::class.java)
        var mList = ArrayList<Suggest>()
        if (sValue != null && sValue.mList != null) {
            mList = sValue.mList
            Collections.sort(mList) { o1, o2 -> o1.dataStr.compareTo(o2.dataStr)}
            if (mList[mList.size - 1].dataStr == getYMD()) {
                mList[mList.size - 1].value = value
            } else {
                val suggest = Suggest()
                suggest.value = value
                suggest.dataStr = getYMD()
                mList.add(suggest)
            }
        } else {
            val suggest = Suggest()
            suggest.value = value
            suggest.dataStr = getYMD()
            mList.add(suggest)
        }
        var tempList = mList
        if (mList.size > 5) {
            for (i in 1 until mList.size) {
                tempList[i - 1] = mList[i]
            }
        }
        val suggestValue = SuggestValue()
        suggestValue.mList = tempList
        sp.setValue(spKey, Gson().toJson(suggestValue))

        val sb = StringBuffer()
        for (i in 0 until tempList.size) {
            sb.append(tempList[i].dataStr + "  " + tempList[i].value + "\n")
        }

        return sb.toString()
    }

    private fun querySuggest(spKey: String): String {
        val sValue = Gson().fromJson(sp.getValue(spKey), SuggestValue::class.java)
        var mList = ArrayList<Suggest>()
        if (sValue != null && sValue.mList != null) {
            mList = sValue.mList
            Collections.sort(mList) { o1, o2 -> o1.dataStr.compareTo(o2.dataStr)}
        }
        val sb = StringBuffer()
        for (i in 0 until mList.size) {
            sb.append(mList[i].dataStr + "  " + mList[i].value + "\n")
        }

        return sb.toString()
    }

    private fun querySuggestList(spKey: String): List<Suggest> {
        val sValue = Gson().fromJson(sp.getValue(spKey), SuggestValue::class.java)
        var mList = ArrayList<Suggest>()
        if (sValue != null && sValue.mList != null) {
            mList = sValue.mList
            Collections.sort(mList) { o1, o2 -> o1.dataStr.compareTo(o2.dataStr)}
        }

        return mList
    }
}

fun createZtsFragment(): ZtsFragment {
    return ZtsFragment()
}