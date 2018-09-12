package ball.drive.com.nbaball

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import ball.drive.com.nbaball.qiutan.MainBean
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textColor

class TDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tdetail)

        val mainBean = intent.getSerializableExtra("mainBean") as MainBean
        fillItem(mainBean, R.id.detailLayout)
        initView()
    }

    private fun initView() {
        findViewById<Button>(R.id.zzButton).onClick {

        }

        findViewById<Button>(R.id.zkButton).onClick {

        }

        findViewById<Button>(R.id.dzButton).onClick {

        }

        findViewById<Button>(R.id.dkButton).onClick {

        }

        findViewById<Button>(R.id.szButton).onClick {

        }

        findViewById<Button>(R.id.skButton).onClick {

        }

        findViewById<Button>(R.id.qzButton).onClick {

        }

        findViewById<Button>(R.id.qkButton).onClick {

        }
    }

    private fun fillItem(mainBean: MainBean, parentId: Int) {
        findViewById<LinearLayout>(parentId)?.removeAllViews()
        val rootViews = LayoutInflater.from(this).inflate(R.layout.fragment_parser_item, null, false)
        rootViews.findViewById<TextView>(R.id.bisai).text = mainBean.liansai
        rootViews.findViewById<TextView>(R.id.time).text = mainBean.time
        rootViews.findViewById<TextView>(R.id.zhudiu).text = mainBean.getZhu()
        rootViews.findViewById<TextView>(R.id.kedui).text = mainBean.getKe()
        rootViews.findViewById<TextView>(R.id.panText).text = getEndPan(mainBean)
        rootViews.findViewById<TextView>(R.id.resultText).text = mainBean.bifen
        val bifen = mainBean.bifen
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
        rootViews.findViewById<LinearLayout>(R.id.parseYaLayout).visibility = View.VISIBLE
        parseYa(rootViews.findViewById(R.id.parseYaLayout), mainBean)

        rootViews.findViewById<TextView>(R.id.yaText).onClick {
            val layout = rootViews.findViewById<LinearLayout>(R.id.parseYaLayout)
            if (layout.visibility == View.VISIBLE) {
                layout.visibility = View.GONE
            } else {
                layout.visibility = View.VISIBLE
            }
        }

        val lps = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        findViewById<LinearLayout>(parentId)?.addView(rootViews, lps)
    }

    private fun parseYa(container: LinearLayout, mainBean: MainBean) {
        container.addView(LayoutInflater.from(this).inflate(R.layout.fragment_parser_item_ya_title, null, false))
        for (i in 0 until mainBean.yList.size) {
            if (isFilter(mainBean.yList[i].company)) {
                val rootViews = LayoutInflater.from(this).inflate(R.layout.fragment_parser_item_ya, null, false)
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
        container.addView(LayoutInflater.from(this).inflate(R.layout.fragment_parser_item_ou_title, null, false))
        for (i in 0 until mainBean.oList.size) {
            if (isFilter(mainBean.oList[i].company)) {
                val rootViews = LayoutInflater.from(this).inflate(R.layout.fragment_parser_item_ou, null, false)
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

    private fun getEndPan(mainBean: MainBean): String {
        for (i in 0 until mainBean.yList.size) {
            if (mainBean.yList[i].company.contains("365")) {
                return mainBean.yList[i].endPan
            }
        }

        return ""
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
}
