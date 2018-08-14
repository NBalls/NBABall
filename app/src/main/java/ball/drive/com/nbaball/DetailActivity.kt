package ball.drive.com.nbaball

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import ball.drive.com.nbaball.WebActivity.Companion.startWebActivity
import ball.drive.com.nbaball.qdog.bean.MBean
import com.google.gson.Gson
import me.yokeyword.fragmentation.SupportActivity
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textColor

class DetailActivity : SupportActivity() {

    companion object {
        private val EXTRA_INFO = "extra_info"

        fun startDetailActivity(context: Context, mBean: MBean) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_INFO, Gson().toJson(mBean))
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val json = intent.getStringExtra(EXTRA_INFO)
        val mBean = Gson().fromJson<MBean>(json, MBean::class.java)

        this.findViewById<TextView>(R.id.bisai).text = mBean.liansai
        this.findViewById<TextView>(R.id.time).text = mBean.time
        this.findViewById<TextView>(R.id.zhudui).text = mBean.zudui
        this.findViewById<TextView>(R.id.kedui).text = mBean.kedui
        this.findViewById<TextView>(R.id.yaText).onClick {
            startWebActivity(this, mBean.yUrl)
        }
        this.findViewById<TextView>(R.id.ouText).onClick {
            startWebActivity(this, mBean.oUrl)
        }
        this.findViewById<TextView>(R.id.bigText).onClick {
            startWebActivity(this, mBean.bigUrl)
        }
        this.findViewById<TextView>(R.id.anaText).onClick {
            startWebActivity(this, mBean.analyseUrl)
        }
        // 亚盘
        for (i in 0 until mBean.yList.size) {
            val rootViews = LayoutInflater.from(this).inflate(R.layout.activity_detail_y_item, null, false)
            rootViews.findViewById<TextView>(R.id.company).text = mBean.yList[i].company
            rootViews.findViewById<TextView>(R.id.startZRate).text = mBean.yList[i].startZRate
            rootViews.findViewById<TextView>(R.id.startPan).text = mBean.yList[i].startPan
            rootViews.findViewById<TextView>(R.id.startKRate).text = mBean.yList[i].startKRate
            rootViews.findViewById<TextView>(R.id.endZRate).text = mBean.yList[i].endZRate
            rootViews.findViewById<TextView>(R.id.endPan).text = mBean.yList[i].endPan
            rootViews.findViewById<TextView>(R.id.endKRate).text = mBean.yList[i].endKRate
            if (mBean.yList[i].endZRate.toFloat() > mBean.yList[i].startZRate.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endZRate).textColor = Color.parseColor("#FF0000")
            } else if (mBean.yList[i].endZRate.toFloat() < mBean.yList[i].startZRate.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endZRate).textColor = Color.parseColor("#00FF00")
            } else {
                rootViews.findViewById<TextView>(R.id.endZRate).textColor = Color.parseColor("#000000")
            }

            if (mBean.yList[i].endKRate.toFloat() > mBean.yList[i].startKRate.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endKRate).textColor = Color.parseColor("#FF0000")
            } else if (mBean.yList[i].endKRate.toFloat() < mBean.yList[i].startKRate.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endKRate).textColor = Color.parseColor("#00FF00")
            } else {
                rootViews.findViewById<TextView>(R.id.endKRate).textColor = Color.parseColor("#000000")
            }

            if (Math.abs(mBean.yList[i].endPan.toFloat()) > Math.abs(mBean.yList[i].startPan.toFloat())) {
                rootViews.findViewById<TextView>(R.id.endPan).textColor = Color.parseColor("#FF0000")
            } else if (Math.abs(mBean.yList[i].endPan.toFloat()) < Math.abs(mBean.yList[i].startPan.toFloat())) {
                rootViews.findViewById<TextView>(R.id.endPan).textColor = Color.parseColor("#00FF00")
            } else {
                rootViews.findViewById<TextView>(R.id.endPan).textColor = Color.parseColor("#000000")
            }
            findViewById<LinearLayout>(R.id.yLayout).addView(rootViews)
        }

        // 欧指
        for (i in 0 until mBean.oList.size) {
            val rootViews = LayoutInflater.from(this).inflate(R.layout.activity_detail_o_item, null, false)
            rootViews.findViewById<TextView>(R.id.company).text = mBean.oList[i].company
            rootViews.findViewById<TextView>(R.id.startSRate).text = mBean.oList[i].startS
            rootViews.findViewById<TextView>(R.id.startPRate).text = mBean.oList[i].startP
            rootViews.findViewById<TextView>(R.id.startFRate).text = mBean.oList[i].startF
            rootViews.findViewById<TextView>(R.id.endSRate).text = mBean.oList[i].endS
            rootViews.findViewById<TextView>(R.id.endPRate).text = mBean.oList[i].endP
            rootViews.findViewById<TextView>(R.id.endFRate).text = mBean.oList[i].endF
            if (mBean.oList[i].endS.toFloat() > mBean.oList[i].startS.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endSRate).textColor = Color.parseColor("#FF0000")
            } else if (mBean.oList[i].endS.toFloat() < mBean.oList[i].startS.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endSRate).textColor = Color.parseColor("#00FF00")
            } else {
                rootViews.findViewById<TextView>(R.id.endSRate).textColor = Color.parseColor("#000000")
            }

            if (mBean.oList[i].endP.toFloat() > mBean.oList[i].startP.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endPRate).textColor = Color.parseColor("#FF0000")
            } else if (mBean.oList[i].endP.toFloat() < mBean.oList[i].startP.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endPRate).textColor = Color.parseColor("#00FF00")
            } else {
                rootViews.findViewById<TextView>(R.id.endPRate).textColor = Color.parseColor("#000000")
            }

            if (mBean.oList[i].endF.toFloat() > mBean.oList[i].startF.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endFRate).textColor = Color.parseColor("#FF0000")
            } else if (mBean.oList[i].endF.toFloat() < mBean.oList[i].startF.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endFRate).textColor = Color.parseColor("#00FF00")
            } else {
                rootViews.findViewById<TextView>(R.id.endFRate).textColor = Color.parseColor("#000000")
            }
            findViewById<LinearLayout>(R.id.oLayout).addView(rootViews)
        }

        // 大小
        for (i in 0 until mBean.bList.size) {
            val rootViews = LayoutInflater.from(this).inflate(R.layout.activity_detail_b_item, null, false)
            rootViews.findViewById<TextView>(R.id.company).text = mBean.bList[i].company
            rootViews.findViewById<TextView>(R.id.startBRate).text = mBean.bList[i].startB
            rootViews.findViewById<TextView>(R.id.startPan).text = mBean.bList[i].startPan.substring(0, mBean.bList[i].startPan.indexOf("球"))
            rootViews.findViewById<TextView>(R.id.startSRate).text = mBean.bList[i].startS
            rootViews.findViewById<TextView>(R.id.endBRate).text = mBean.bList[i].endB
            rootViews.findViewById<TextView>(R.id.endPan).text = mBean.bList[i].endPan.substring(0, mBean.bList[i].endPan.indexOf("球"))
            rootViews.findViewById<TextView>(R.id.endSRate).text = mBean.bList[i].endS
            if (mBean.bList[i].endB.toFloat() > mBean.bList[i].startB.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endBRate).textColor = Color.parseColor("#FF0000")
            } else if (mBean.bList[i].endB.toFloat() < mBean.bList[i].startB.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endBRate).textColor = Color.parseColor("#00FF00")
            } else {
                rootViews.findViewById<TextView>(R.id.endBRate).textColor = Color.parseColor("#000000")
            }

            /*val endPan = mBean.bList[i].endPan.substring(0, mBean.bList[i].endPan.indexOf("球"))
            val startPan = mBean.bList[i].startPan.substring(0, mBean.bList[i].startPan.indexOf("球"))
            if (endPan.toFloat() > startPan.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endPan).textColor = Color.parseColor("#FF0000")
            } else if (endPan.toFloat() < startPan.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endPan).textColor = Color.parseColor("#00FF00")
            } else {
                rootViews.findViewById<TextView>(R.id.endPan).textColor = Color.parseColor("#000000")
            }*/

            if (mBean.bList[i].endS.toFloat() > mBean.bList[i].startS.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endSRate).textColor = Color.parseColor("#FF0000")
            } else if (mBean.bList[i].endS.toFloat() < mBean.bList[i].startS.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endSRate).textColor = Color.parseColor("#00FF00")
            } else {
                rootViews.findViewById<TextView>(R.id.endSRate).textColor = Color.parseColor("#000000")
            }
            findViewById<LinearLayout>(R.id.bLayout).addView(rootViews)
        }

        // 对往比赛
        for (i in 0 until mBean.fList.size) {
            val rootViews = LayoutInflater.from(this).inflate(R.layout.activity_detail_f_item, null, false)
            rootViews.findViewById<TextView>(R.id.company).text = mBean.fList[i].company
            rootViews.findViewById<TextView>(R.id.time).text = mBean.fList[i].date
            rootViews.findViewById<TextView>(R.id.zhudui).text = mBean.fList[i].zhudui
            rootViews.findViewById<TextView>(R.id.point).text = mBean.fList[i].zhuPoint + " VS " + mBean.fList[i].kePoint
            rootViews.findViewById<TextView>(R.id.kedui).text = mBean.fList[i].kedui
            rootViews.findViewById<TextView>(R.id.startYZRate).text = mBean.fList[i].ys
            rootViews.findViewById<TextView>(R.id.startYPan).text = mBean.fList[i].yp
            rootViews.findViewById<TextView>(R.id.startYKRate).text = mBean.fList[i].yf
            rootViews.findViewById<TextView>(R.id.startBBRate).text = mBean.fList[i].bb
            rootViews.findViewById<TextView>(R.id.startBPan).text = mBean.fList[i].bp
            rootViews.findViewById<TextView>(R.id.startBSRate).text = mBean.fList[i].bs
            rootViews.findViewById<TextView>(R.id.result).text = mBean.fList[i].nresult
            rootViews.findViewById<TextView>(R.id.rresult).text = mBean.fList[i].yresult
            rootViews.findViewById<TextView>(R.id.bresult).text = mBean.fList[i].bresult

            /*rootViews.findViewById<TextView>(R.id.endPan).text = mBean.bList[i].endPan.substring(0, mBean.bList[i].endPan.indexOf("球"))
            rootViews.findViewById<TextView>(R.id.endSRate).text = mBean.bList[i].endS
            if (mBean.bList[i].endB.toFloat() > mBean.bList[i].startB.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endBRate).textColor = Color.parseColor("#FF0000")
            } else if (mBean.bList[i].endB.toFloat() < mBean.bList[i].startB.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endBRate).textColor = Color.parseColor("#00FF00")
            } else {
                rootViews.findViewById<TextView>(R.id.endBRate).textColor = Color.parseColor("#000000")
            }

            *//*val endPan = mBean.bList[i].endPan.substring(0, mBean.bList[i].endPan.indexOf("球"))
            val startPan = mBean.bList[i].startPan.substring(0, mBean.bList[i].startPan.indexOf("球"))
            if (endPan.toFloat() > startPan.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endPan).textColor = Color.parseColor("#FF0000")
            } else if (endPan.toFloat() < startPan.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endPan).textColor = Color.parseColor("#00FF00")
            } else {
                rootViews.findViewById<TextView>(R.id.endPan).textColor = Color.parseColor("#000000")
            }*//*

            if (mBean.bList[i].endS.toFloat() > mBean.bList[i].startS.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endSRate).textColor = Color.parseColor("#FF0000")
            } else if (mBean.bList[i].endS.toFloat() < mBean.bList[i].startS.toFloat()) {
                rootViews.findViewById<TextView>(R.id.endSRate).textColor = Color.parseColor("#00FF00")
            } else {
                rootViews.findViewById<TextView>(R.id.endSRate).textColor = Color.parseColor("#000000")
            }*/
            findViewById<LinearLayout>(R.id.fLayout).addView(rootViews)
        }
    }
}
