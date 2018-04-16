package ball.drive.com.nbaball.official

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import ball.drive.com.nbaball.BaseFragment
import ball.drive.com.nbaball.R

/**
 * Created by aaron on 2018/4/11.
 */
class ZtsFragment: BaseFragment() {

    private var temp: String = "\uD83C\uDF89再次爆红！[奸笑]侦探号所向无敌[机智]昨天足球公推再次击中，精选双红，重心止步四连红，篮球致富单依旧还是红[耶]总体5中4！近6日更是23中17！仅6日盈利1.5A\uD83D\uDE31[红包]侦探出击，所向披靡！[發]\uD83D\uDE0F\n" +
            "\n" +
            "今日侦探出击赞赏推荐[嘿哈]\n" +
            "\uD83D\uDC4D足球致富单：188不红退款等你约[玫瑰]\n" +
            "[耶]精选足球两场：99祝你久久长红[爱心]\n" +
            "[太阳]打包致富单精选单：166愿你永远发发发[西瓜]\n" +
            "\n" +
            "[爱心]篮球致富单：180不红包退，就是这么牛掰[白眼]\n" +
            "\n" +
            "\uD83C\uDFC0今日篮球包月1699，每日2-3场\uD83D\uDCAA；\n" +
            "⚽足球今日包月只要1599[机智]，每日3-4场\uD83C\uDFA4；\n" +
            "\n" +
            "    足篮同包月更是折上折，今日只要2999[阴险]，只要2999，即可享受侦探专家每天三场足球，三场篮球。还不抓紧报名！！\uD83D\uDC2C"

    private var temp2: String = "\uD83C\uDF89再次爆红！[奸笑]侦探号所向无敌[机智]昨天足球公推再次击中，精选双红，重心止步四连红，篮球致富单依旧还是红[耶]总体5中4！近6日更是23中17！仅6日盈利1.5A\uD83D\uDE31[红包]侦探出击，所向披靡！[發]\uD83D\uDE0F\n" +
            "\n" +
            "\uD83C\uDFC0今日篮球包月1699,⚽足球包月1599。通包只需2999\uD83C\uDF81"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_zts, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.findViewById<EditText>(R.id.contentEdit)?.setText(temp)
        view?.findViewById<EditText>(R.id.contentEdit2)?.setText(temp2)
    }
}

fun createZtsFragment(): ZtsFragment {
    return ZtsFragment()
}