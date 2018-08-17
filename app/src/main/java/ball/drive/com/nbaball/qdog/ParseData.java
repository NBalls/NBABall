package ball.drive.com.nbaball.qdog;

import android.text.TextUtils;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import ball.drive.com.nbaball.OnParseListener;
import ball.drive.com.nbaball.qdog.bean.BBean;
import ball.drive.com.nbaball.qdog.bean.FBean;
import ball.drive.com.nbaball.qdog.bean.MBean;
import ball.drive.com.nbaball.qdog.bean.OBean;
import ball.drive.com.nbaball.qdog.bean.RBean;
import ball.drive.com.nbaball.qdog.bean.YBean;
import ball.drive.com.nbaball.qdog.util.ParserUtil;

/**
 * Created by aaron on 2018/7/24.
 * 解析数据
 */
public class ParseData {

    private static final String NET_URL = "https://www.liaogou168.com/match/immediate.html";
    private static final String CLASS_TABLE = "contentTable";
    private static final String TAB_TBODY = "tbody";
    private static final String TAG = "ParserFragment";
    private static final List<MBean> mainList = new ArrayList();
    public static OnParseListener onParseListener = null;

    /**
     * 解析比赛数据--主要解析当前尚未开赛的比赛
     * 若比赛数量较大则最多解析400场比赛
     */
    public static List<MBean> parseMainData(int parseCount) throws Exception {
        Document doc = Jsoup.connect(NET_URL).get();
        Elements tableElement = doc.getElementsByClass(CLASS_TABLE);
        if (tableElement == null || tableElement.size() <= 0) {
            return mainList;
        }
        Element tabElement = tableElement.get(0);
        Elements tbodyElement = tabElement.getElementsByTag(TAB_TBODY);
        if (tbodyElement == null || tbodyElement.size() <= 0) {
            return mainList;
        }
        Element bodyElement = tbodyElement.get(0);
        Elements trElement = bodyElement.children();
        mainList.clear();
        // 若总数据大于300条，则先解析300条，否则内存会被打满
        for (int i = 0; i < Math.min(trElement.size(), parseCount); i++) {
            MBean mBean = new MBean();
            Element element = trElement.get(i);
            if (!element.child(9).child(0).text().trim().equals("---")
                    && TextUtils.isEmpty(element.child(3).text().trim())) {
                Log.i(TAG, "###### 开始解析第 " + (i + 1) + " 条数据......");
                if (onParseListener != null) {
                    onParseListener.onParseRate((i + i), true);
                }
                mBean.setId(element.attr("match"));
                mBean.setLiansai(element.child(1).child(0).text().trim());
                mBean.setTime(element.child(2).text().trim().substring(0, 5));
                mBean.setStatus(element.child(3).text().trim());
                mBean.setZudui(element.child(4).child(0).text().trim());
                mBean.setZhuUrl(element.child(4).child(0).attr("href"));
                mBean.setKeUrl(element.child(6).child(0).attr("href"));
                String points = element.child(5).child(0).child(0).text().trim();
                if (points.indexOf("-") >= 0 && points.indexOf("-") < points.length()) {
                    mBean.setZhuPoint(points.substring(0, points.indexOf("-")));
                    mBean.setKedui(points.substring(points.indexOf("-") + 1));
                }
                mBean.setKedui(element.child(6).child(0).text().trim());
                mBean.setAnalyseUrl(element.child(10).child(0).attr("href"));
                mBean.setAsiaUrl(element.child(10).child(1).attr("href"));
                mBean.setBigUrl(element.child(10).child(2).attr("href"));
                mBean.setOuUrl(element.child(10).child(3).attr("href"));
                parseYData(mBean);
            } else {
                if (onParseListener != null) {
                    onParseListener.onParseRate((i + i), false);
                }
                Log.i(TAG, "###### " + (i + 1) + " 条数据为无效数据不在解析......");
            }

        }

        return mainList;
    }

    /**
     * 解析亚盘盘口数需要大于等于3
     */
    private static void parseYData(MBean mBean) throws Exception {
        Document doc = Jsoup.connect(mBean.getYUrl()).get();
        Element divElement = doc.getElementById("Odd");
        Elements bodyElement = divElement.getElementsByTag(TAB_TBODY);
        if (bodyElement.size() <= 0) {
            return;
        }
        Elements trElement = bodyElement.get(0).children();
        if (trElement.size() <= 0) {
            return;
        }
        List<YBean> yList = new ArrayList();
        for (int i = 0; i < trElement.size(); i ++) {
            YBean yBean = new YBean();
            yBean.setCompany(trElement.get(i).child(0).text().trim());
            yBean.setStartZRate(trElement.get(i).child(1).text().trim());
            yBean.setStartPan(ParserUtil.changePan(trElement.get(i).child(2).text().trim()));
            yBean.setStartKRate(trElement.get(i).child(3).text().trim());
            yBean.setEndZRate(trElement.get(i).child(4).text().trim());
            yBean.setEndPan(ParserUtil.changePan(trElement.get(i).child(5).text().trim()));
            yBean.setEndKRate(trElement.get(i).child(6).text().trim());
            yList.add(yBean);
        }

        if (yList.size() >= 3) {
            mBean.setyList(yList);
            parseOData(mBean);
        } else {
            Log.i(TAG, "###### 比赛亚盘数量小于3，放弃该比赛........");
        }
    }

    /**
     * 解析欧盘盘口数需要大于等于3
     */
    private static void parseOData(MBean mBean) throws Exception {
        Document doc = Jsoup.connect(mBean.getOUrl()).get();
        Element divElement = doc.getElementById("Odd");
        Elements bodyElement = divElement.getElementsByTag("tbody");
        if (bodyElement.size() <= 0) {
            return;
        }
        Elements trElement = bodyElement.get(0).children();
        if (trElement.size() <= 0) {
            return;
        }
        List<OBean> oList = new ArrayList();
        for (int i = 0; i < trElement.size(); i ++) {
            OBean oBean = new OBean();
            oBean.setCompany(trElement.get(i).child(0).text().trim());
            oBean.setStartS(trElement.get(i).child(1).text().trim());
            oBean.setStartP(trElement.get(i).child(2).text().trim());
            oBean.setStartF(trElement.get(i).child(3).text().trim());
            oBean.setEndS(trElement.get(i).child(4).text().trim());
            oBean.setEndP(trElement.get(i).child(5).text().trim());
            oBean.setEndF(trElement.get(i).child(6).text().trim());
            if (TextUtils.isEmpty(oBean.getStartS())
                    || TextUtils.isEmpty(oBean.getStartP())
                    || TextUtils.isEmpty(oBean.getStartF())
                    || TextUtils.isEmpty(oBean.getEndS())
                    || TextUtils.isEmpty(oBean.getEndP())
                    || TextUtils.isEmpty(oBean.getEndF())) {
                continue;
            }
            oList.add(oBean);
        }

        if (oList.size() >= 3) {
            mBean.setoList(oList);
            parserBData(mBean);
        } else {
            Log.i(TAG, "###### 比赛欧盘数量小于3，放弃该比赛........");
        }
    }

    /**
     * 解析大小(盘口数需要大于等于3)
     */
    private static void parserBData(MBean mBean) throws Exception {
        Document doc = Jsoup.connect(mBean.getBigUrl()).get();
        Element divElement = doc.getElementById("Odd");
        Elements bodyElement = divElement.getElementsByTag("tbody");
        if (bodyElement.size() <= 0) {
            return;
        }
        Elements trElement = bodyElement.get(0).children();
        if (trElement.size() <= 0) {
            return;
        }
        List<BBean> bList = new ArrayList();
        for (int i = 0; i < trElement.size(); i ++) {
            BBean bBean = new BBean();
            bBean.setCompany(trElement.get(i).child(0).text().trim());
            bBean.setStartB(trElement.get(i).child(1).text().trim());
            bBean.setStartPan(trElement.get(i).child(2).text().trim());
            bBean.setStartS(trElement.get(i).child(3).text().trim());
            bBean.setEndB(trElement.get(i).child(4).text().trim());
            bBean.setEndPan(trElement.get(i).child(5).text().trim());
            bBean.setEndS(trElement.get(i).child(6).text().trim());
            bList.add(bBean);
        }

        if (bList.size() >= 3) {
            mBean.setbList(bList);
            parserFData(mBean);
        } else {
            Log.i(TAG, "###### 比赛大小盘数量小于3，放弃该比赛........");
        }
    }

    /**
     * 解析以往比赛至少需要对往2次比赛
     */
    private static void parserFData(MBean mBean) throws Exception {
        Document doc = Jsoup.connect(mBean.getAnalyseUrl()).get();

        Element teamElement = doc.getElementById("Content");
        String homeTeam = teamElement.child(1).child(1).child(1).child(1).text().trim();
        String awayTeam = teamElement.child(1).child(3).child(1).child(1).text().trim();
        if (!TextUtils.isEmpty(homeTeam) && homeTeam.contains("【") && homeTeam.contains("】")) {
            String homeTeamStr = homeTeam.substring(homeTeam.indexOf("【") + 1, homeTeam.indexOf("】"));
            mBean.setZhuRank(homeTeamStr);
        }
        if (!TextUtils.isEmpty(awayTeam) && awayTeam.contains("【") && awayTeam.contains("】")) {
            String awayTeamStr = awayTeam.substring(awayTeam.indexOf("【") + 1, awayTeam.indexOf("】"));
            mBean.setKeRank(awayTeamStr);
        }
        Element divElement = doc.getElementById("Data_Battle");
        if (divElement == null) {
            return;
        }
        Element bodyElement = divElement.child(1).child(1).child(1);
        Elements trElement = bodyElement.children();
        if (trElement.size() <= 0) {
            return;
        }
        List<FBean> fList = new ArrayList();
        for (int i = 0; i < trElement.size(); i ++) {
            FBean fBean = new FBean();
            fBean.setCompany(trElement.get(i).child(0).child(0).text().trim());
            fBean.setDate(trElement.get(i).child(1).text().trim());
            fBean.setZhudui(trElement.get(i).child(2).text().trim());
            fBean.setKedui(trElement.get(i).child(4).text().trim());
            String points = trElement.get(i).child(3).child(0).text().trim();
            fBean.setZhuPoint(points.substring(0, points.indexOf("-")).trim());
            fBean.setKePoint(points.substring(points.indexOf("-") + 1));
            fBean.setOs(trElement.get(i).child(5).text().trim());
            fBean.setOp(trElement.get(i).child(6).text().trim());
            fBean.setOf(trElement.get(i).child(7).text().trim());
            fBean.setOs2(trElement.get(i).child(8).text().trim());
            fBean.setOp2(trElement.get(i).child(9).text().trim());
            fBean.setOf2(trElement.get(i).child(10).text().trim());

            fBean.setYs(trElement.get(i).child(11).text().trim());
            fBean.setYp(ParserUtil.changePan(trElement.get(i).child(12).text().trim()));
            fBean.setYf(trElement.get(i).child(13).text().trim());
            fBean.setYs2(trElement.get(i).child(14).text().trim());
            fBean.setYp2(ParserUtil.changePan(trElement.get(i).child(15).text().trim()));
            fBean.setYf2(trElement.get(i).child(16).text().trim());

            fBean.setBb(trElement.get(i).child(17).text().trim());
            fBean.setBp(trElement.get(i).child(18).text().trim());
            fBean.setBs(trElement.get(i).child(19).text().trim());
            fBean.setBb2(trElement.get(i).child(20).text().trim());
            fBean.setBp2(trElement.get(i).child(21).text().trim());
            fBean.setBs2(trElement.get(i).child(22).text().trim());

            fBean.setNresult(trElement.get(i).child(23).text().trim());
            fBean.setYresult(trElement.get(i).child(24).text().trim());
            fBean.setBresult(trElement.get(i).child(25).text().trim());
            fList.add(fBean);
        }
        // 对往比赛--一年内比过1次，两年内比过2次
        if (fList.size() >= 2) {
            mBean.setfList(fList);
            parserRData(mBean);
        } else {
            Log.i(TAG, "###### 对往比赛小于2次........");
        }
    }

    /**
     * 解析近期比赛
     */
    private static void parserRData(MBean mBean) throws Exception {
        Document doc = Jsoup.connect(mBean.getAnalyseUrl()).get();
        Element divElement = doc.getElementById("Data_History");
        if (divElement == null) {
            return;
        }
        if (divElement.children().size() >= 3 && divElement.child(1) != null) {
            Element bodyElement = divElement.child(1).child(1).child(2).child(1);
            Elements elements = bodyElement.children();
            List<RBean> zList = new ArrayList();
            for (int i = 0; i < Math.min(elements.size(), 5); i ++) {
                RBean rBean = new RBean();
                rBean.setNresult(elements.get(i).child(23).text().trim());
                rBean.setRresult(elements.get(i).child(24).text().trim());
                rBean.setBresult(elements.get(i).child(25).text().trim());
                zList.add(rBean);
            }
            mBean.setzList(zList);
        }

        if (divElement.children().size() >= 3 && divElement.child(2) != null) {
            Element bodyElement1 = divElement.child(2).child(1).child(2).child(1);
            Elements elements1 = bodyElement1.children();
            List<RBean> kList = new ArrayList();
            for (int i = 0; i < Math.min(elements1.size(), 5); i ++) {
                RBean rBean = new RBean();
                rBean.setNresult(elements1.get(i).child(23).text().trim());
                rBean.setRresult(elements1.get(i).child(24).text().trim());
                rBean.setBresult(elements1.get(i).child(25).text().trim());
                kList.add(rBean);
            }
            mBean.setkList(kList);
        }
        mainList.add(mBean);
    }

    /**
     * 解析1.44比赛
     * @param mainList
     */
    public static List<MBean> print144(List<MBean> mainList) {
        List<MBean> mList = new ArrayList();
        for (int i = 0; i < mainList.size(); i ++) {
            int count = 0;
            for (int j = 0; j < mainList.get(i).getoList().size(); j ++) {
                if (mainList.get(i).getoList().get(j).endS.equals("1.44") ||
                        mainList.get(i).getoList().get(j).endF.equals("1.44")) {
                    count = count + 1;
                }
            }

            if (count >= 2) {
                mList.add(mainList.get(i));
            }
        }

        return mList;
    }

    /**
     * 解析下盘低水比赛
     * @param mainList
     */
    public static List<MBean> printDown(List<MBean> mainList) {
        List<MBean> mList = new ArrayList();
        for (int i = 0; i < mainList.size(); i ++) {
            int count = 0;
            for (int j = 0; j < mainList.get(i).getyList().size(); j ++) {
                if (Float.valueOf(mainList.get(i).getyList().get(j).endPan) > 0 &&
                        Float.valueOf(mainList.get(i).getyList().get(j).endPan) < 1 &&
                        Float.valueOf(mainList.get(i).getyList().get(j).getEndKRate()) -
                                Float.valueOf(mainList.get(i).getyList().get(j).getStartKRate()) < 0) {
                    count = count + 1;
                } else if (Float.valueOf(mainList.get(i).getyList().get(j).endPan) < 0 &&
                        Float.valueOf(mainList.get(i).getyList().get(j).endPan) > -1 &&
                        Float.valueOf(mainList.get(i).getyList().get(j).getEndZRate()) -
                                Float.valueOf(mainList.get(i).getyList().get(j).getStartZRate()) < 0) {
                    count = count + 1;
                }
            }

            if (count >= mainList.get(i).getyList().size()) {
                mList.add(mainList.get(i));
            }
        }

        return mList;
    }

    /**
     * 解析大2.75比赛
     * @param mainList
     */
    public static List<MBean> print275(List<MBean> mainList) {
        List<MBean> mList = new ArrayList();
        for (int i = 0; i < mainList.size(); i ++) {
            int count = 0;
            for (int j = 0; j < mainList.get(i).getbList().size(); j ++) {
                if (mainList.get(i).getbList().get(j).endPan.startsWith("2.5/3") &&
                        Float.valueOf(mainList.get(i).getbList().get(j).getStartB()) -
                        Float.valueOf(mainList.get(i).getbList().get(j).getEndB()) >= 0) {
                    count = count + 1;
                }
            }

            if (count >= mainList.get(i).getbList().size()) {
                mList.add(mainList.get(i));
            }
        }

        return mList;
    }

    /**
     * 解析半一盘比赛
     * @param mainList
     */
    public static List<MBean> print075(List<MBean> mainList) {
        List<MBean> mList = new ArrayList();
        for (int i = 0; i < mainList.size(); i ++) {
            int count = 0;
            for (int j = 0; j < mainList.get(i).getyList().size(); j ++) {
                if (mainList.get(i).getyList().get(j).getEndPan().equals("0.75") &&
                        Float.valueOf(mainList.get(i).getyList().get(j).getEndZRate()) -
                        Float.valueOf(mainList.get(i).getyList().get(j).getStartZRate()) >= 0) {
                    count = count + 1;
                }
                if (mainList.get(i).getyList().get(j).getEndPan().equals("-0.75") &&
                        Float.valueOf(mainList.get(i).getyList().get(j).getStartKRate()) -
                                Float.valueOf(mainList.get(i).getyList().get(j).getEndKRate()) >= 0) {
                    count = count + 1;
                }
            }

            if (count >= mainList.get(i).getyList().size()) {
                mList.add(mainList.get(i));
            }
        }

        return mList;
    }

    /**
     * 解析连续变盘2个盘口以上比赛
     * @param mainList
     */
    public static List<MBean> print2Over(List<MBean> mainList) {
        List<MBean> mList = new ArrayList();
        for (int i = 0; i < mainList.size(); i ++) {
            int count = 0;
            for (int j = 0; j < mainList.get(i).getyList().size(); j ++) {
                if (Float.valueOf(mainList.get(i).getyList().get(j).getEndPan()) -
                        Float.valueOf(mainList.get(i).getyList().get(j).getStartPan()) >= 0.5 ||
                        Float.valueOf(mainList.get(i).getyList().get(j).getEndPan()) -
                                Float.valueOf(mainList.get(i).getyList().get(j).getStartPan()) <= -0.5) {
                    count = count + 1;
                }
            }

            if (count >= mainList.get(i).getyList().size() - 1) {
                mList.add(mainList.get(i));
            }
        }
        return mList;
    }

    /**
     * 解析与近期比赛让球盘变化较大比赛
     * @param mainList
     */
    public static List<MBean> printCOver(List<MBean> mainList) {
        List<MBean> mList = new ArrayList();
        for (int i = 0; i < mainList.size(); i ++) {
            int count = 0;
            if (mainList.get(i).getfList().size() > 0 && mainList.get(i).getyList().size() > 0 &&
                    ParserUtil.isNearDate(mainList.get(i).getfList().get(0).getDate())) {
                if (mainList.get(i).getZudui().equals(mainList.get(i).getfList().get(0).getZhudui())) {
                    if (Float.valueOf(mainList.get(i).getyList().get(0).getEndPan()) -
                            Float.valueOf(mainList.get(i).getfList().get(0).getYp()) >= 0.25) {
                        count = count + 1;
                    } else if (Float.valueOf(mainList.get(i).getyList().get(0).getEndPan()) -
                            Float.valueOf(mainList.get(i).getfList().get(0).getYp()) <= -0.25) {
                        count = count + 1;
                    }
                }
            }

            if (count >= 1) {
                mList.add(mainList.get(i));
            }
        }
        return mList;
    }

    /**
     * 解析一直是平手盘比赛
     * @param mainList
     */
    public static List<MBean> printZero(List<MBean> mainList) {
        List<MBean> mList = new ArrayList();
        for (int i = 0; i < mainList.size(); i ++) {
            int count = 0;
            if (mainList.get(i).getyList().size() > 0) {
                for (int j = 0; j < mainList.get(i).getyList().size(); j ++) {
                    if (mainList.get(i).getyList().get(j).getStartPan().equals("0.0")
                            && mainList.get(i).getyList().get(j).getEndPan().equals("0.0")) {
                        count = count + 1;
                    }
                }
            }

            if (count >= mainList.size() / 2) {
                mList.add(mainList.get(i));
            }
        }
        return mList;
    }

    /**
     * 解析半球盘将至请半盘
     * @param mainList
     */
    public static List<MBean> printBan(List<MBean> mainList) {
        List<MBean> mList = new ArrayList();
        for (int i = 0; i < mainList.size(); i ++) {
            int count = 0;
            if (mainList.get(i).getyList().size() > 0) {
                for (int j = 0; j < mainList.get(i).getyList().size(); j ++) {
                    if (mainList.get(i).getyList().get(j).getStartPan() == "0.25"
                            && mainList.get(i).getyList().get(j).getEndPan() == "0.5") {
                        count = count + 1;
                    }
                }
            }

            if (count >= mainList.size() / 2) {
                mList.add(mainList.get(i));
            }
        }
        return mList;
    }

    // TODO 升盘降水比赛
    // TODO 降盘升水比赛

    // 解析大小盘口近期比赛变化较大的比赛
    public static void printAll(List<MBean> mainList) {
        for (int i = 0; i < mainList.size(); i ++) {
            System.out.println(mainList.get(i));
        }
    }
}
