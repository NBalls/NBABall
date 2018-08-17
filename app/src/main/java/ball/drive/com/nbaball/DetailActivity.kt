package ball.drive.com.nbaball

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
        this.findViewById<TextView>(R.id.doText).onClick {
            parseMatch(mBean)
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

    /**
     * 解析
     */
    private fun parseMatch(mBean: MBean) {
        val sb = StringBuffer()
        if (TextUtils.isEmpty(mBean.zhuRank) || TextUtils.isEmpty(mBean.keRank)) {
            return
        }
        val zhuRank = mBean.zhuRank.substring(mBean.zhuRank.length - 1).toInt()
        val keRank = mBean.keRank.substring(mBean.keRank.length - 1).toInt()

        // title说明
        sb.append("诸葛神盘--你博彩征途中的军师\n")
        val tempTime = System.currentTimeMillis()
        // 排名说明
        if (tempTime % 3 <= 10L) {
            sb.append("今天诸葛重点关注")
                    .append(mBean.zhuRank)
                    .append("的")
                    .append(mBean.zudui)
                    .append("主场迎战")
                    .append(mBean.keRank)
                    .append("的")
                    .append(mBean.kedui)
                    .append("\n")
        } else if (tempTime % 3 == 1L) {
            sb.append("今天")
                    .append(mBean.zhuRank)
                    .append("的")
                    .append(mBean.zudui)
                    .append("主场对战")
                    .append(mBean.keRank)
                    .append("的")
                    .append(mBean.kedui)
                    .append("战果会如何？且听诸葛慢慢道来")
                    .append("\n")
        } else if (tempTime % 3 == 2L) {
            sb.append("今天")
                    .append(mBean.zhuRank)
                    .append("的")
                    .append(mBean.zudui)
                    .append("将会在主场迎战")
                    .append(mBean.keRank)
                    .append("的")
                    .append(mBean.kedui)
                    .append("具体情况如何？")
                    .append("\n")
        }
        val tempTime1 = System.currentTimeMillis()
        if (tempTime1 % 3 <= 10L) {
            sb.append("从排名上看主队")
                    .append(mBean.zudui)
                    .append("排名第")
                    .append(mBean.zhuRank.substring(mBean.zhuRank.length - 1))
                    .append("而客队")
                    .append(mBean.kedui)
                    .append("排名第")
                    .append(mBean.keRank.substring(mBean.keRank.length - 1)).append(",")
            if (Math.abs(zhuRank - keRank) < 5) {
                sb.append("两队排名相对靠近")
            } else {
                sb.append("两队排名差距较大")
            }
            sb.append("而主场作战的")
                    .append(mBean.zudui)
                    .append("无疑对比")
                    .append(mBean.kedui)
                    .append("更新要一场胜利来提振一下球队士气\n")
        }

        // 历史对战说明
        var winCount = 0
        var failCount = 0
        var withCount = 0
        for (i in 0 until Math.min(mBean.getfList().size, 5)) {
            if (mBean.getfList()[i].nresult == "胜") {
                winCount += 1
            } else if (mBean.getfList()[i].nresult == "平") {
                withCount += 1
            } else {
                failCount += 1
            }
        }
        sb.append("两队交锋历史上，近")
                .append(Math.min(mBean.getfList().size, 5))
                .append("比赛主队")
                .append(mBean.getZudui())
                .append("录得")
                .append(winCount).append("胜")
                .append(withCount).append("平")
                .append(failCount).append("负")
                .append("的战绩。")
        if (winCount > failCount) {
            sb.append("主队占据一定的优势")
        } else if (winCount < failCount) {
            sb.append("客队占据一定的优势")
        } else {
            sb.append("两队历史对战战果平分秋色")
        }


        // 近期战况说明
        val winCount1 = 0
        val failCount1 = 0
        val withCount1 = 0
        for (i in 0 until Math.min(mBean.getzList().size, 5)) {

        }
                "从排名上看蒙彼利埃无意更具有优势但是两队都是10名以外的球队排名更只是具有象征性的意义，参考性不大。" +
                        "而在第一轮法甲比赛中亚眠与蒙彼利埃都是首站告负本场比赛而言两队战意都比较足，但是相对于客场作战的蒙彼利埃而言。" +
                        "主场作战的亚眠无疑更需要一场胜利来振奋一下球队。\n" +
                "\n" +
                "两队交锋史上基本可以说是平分秋色尤其是近两场次交锋录得三场平局，近两年两队更是只有两次交锋且都是以平局收尾。\n" +
                "\n" +
                "近期比赛中亚眠近5场比赛1胜1平3负尤其是近3场1平2负可谓状态较为低迷，而蒙彼利埃近5场比赛2胜2平1负状态一般近3场比赛1胜1平1负相对而言交亚眠无疑根据优势。\n" +
                "\n" +
                "亚盘初盘以亚眠让平半盘中低水开出相对而言对亚眠的支持力度较大，但是后市收注之后主队水位持续上升客队水位持续下降说明客队热度不减，" +
                        "欧指方面平负指数同时调低说明机构对客队至少不败抱有信心，综上所述本场比赛更应该支持蒙彼利埃不败。"

        Log.i("DetailActivity", "#########" + sb.toString())
    }
}
