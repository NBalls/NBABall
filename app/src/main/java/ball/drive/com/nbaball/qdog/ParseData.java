package ball.drive.com.nbaball.qdog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import ball.drive.com.nbaball.qdog.bean.MBean;
import ball.drive.com.nbaball.qdog.bean.OBean;
import ball.drive.com.nbaball.qdog.bean.RBean;
import ball.drive.com.nbaball.qdog.bean.YBean;

/**
 * Created by aaron on 2018/7/24.
 */

public class ParseData {

    public static final String NET_URL = "https://www.liaogou168.com/match/immediate.html";
    public static final String CLASS_TABLE = "contentTable";
    public static final String TAB_TBODY = "tbody";
    public static final List<MBean> mainList = new ArrayList();

    public static void parseMainData() throws Exception {
        Document doc = Jsoup.connect(NET_URL).get();
        Elements tableElement = doc.getElementsByClass(CLASS_TABLE);
        if (tableElement == null || tableElement.size() <= 0) {
            return;
        }
        Element tabElement = tableElement.get(0);
        Elements tbodyElement = tabElement.getElementsByTag(TAB_TBODY);
        if (tbodyElement == null || tbodyElement.size() <= 0) {
            return;
        }
        Element bodyElement = tbodyElement.get(0);
        Elements trElement = bodyElement.children();
        for (int i = 0; i < trElement.size(); i++) {
            MBean mBean = new MBean();
            Element element = trElement.get(i);
            mBean.setId(element.attr("match"));
            mBean.setLiansai(element.child(1).child(0).text().trim());
            mBean.setTime(element.child(2).text().trim());
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

            // System.out.println(mBean);
            parseYData(mBean);
        }

        printTitle(mainList);
    }

    // 解析亚盘
    private static void parseYData(MBean mBean) throws Exception {
        Document doc = Jsoup.connect(mBean.getYUrl()).get();
        Element divElement = doc.getElementById("Odd");
        Elements bodyElement = divElement.getElementsByTag("tbody");
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
            yBean.setStartPan(trElement.get(i).child(2).text().trim());
            yBean.setStartKRate(trElement.get(i).child(3).text().trim());
            yBean.setEndZRate(trElement.get(i).child(4).text().trim());
            yBean.setEndPan(trElement.get(i).child(5).text().trim());
            yBean.setEndKRate(trElement.get(i).child(6).text().trim());
            yList.add(yBean);
        }
        mBean.setyList(yList);
        parseOData(mBean);
        // System.out.println(mBean);
    }

    // 解析欧盘
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
            oList.add(oBean);
        }
        mBean.setoList(oList);
        parserRData(mBean);
        // System.out.println(mBean);
    }

    // 解析近期
    public static void parserRData(MBean mBean) throws Exception {
        Document doc = Jsoup.connect(mBean.getAnalyseUrl()).get();
        Element divElement = doc.getElementById("Data_History");
        if (divElement == null) {
            return;
        }
        if (divElement.children().size() >= 3 && divElement.child(1) != null) {
            Element bodyElement = divElement.child(1).child(1).child(2).child(1);
            Elements elements = bodyElement.children();
            List<RBean> zList = new ArrayList();
            for (int i = 0; i < elements.size(); i ++) {
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
            for (int i = 0; i < elements1.size(); i ++) {
                RBean rBean = new RBean();
                rBean.setNresult(elements1.get(i).child(23).text().trim());
                rBean.setRresult(elements1.get(i).child(24).text().trim());
                rBean.setBresult(elements1.get(i).child(25).text().trim());
                kList.add(rBean);
            }
            mBean.setkList(kList);
        }
        mainList.add(mBean);
        //System.out.println(mBean);
    }

    public static void printTitle(List<MBean> mainList) {
        StringBuffer sb = new StringBuffer();
        sb.append("今日共有比赛：" + mainList.size() + "场");
        int completeCount = 0;
        int nostartCount = 0;
        int playingCount = 0;
        for (int i = 0; i < mainList.size(); i ++) {
            if (mainList.get(i).getStatus().equals("已结束")) {
                completeCount = completeCount + 1;
            } else if (mainList.get(i).getStatus().equals("")) {
                nostartCount = nostartCount + 1;
            } else {
                playingCount = playingCount + 1;
            }
        }
        sb.append("已开始：" + playingCount + "场，未开始：" + nostartCount + "场，已结束：" + completeCount);
        System.out.println(sb.toString());

        print144(mainList);
    }

    /**
     * 解析1.44比赛
     * @param mainList
     */
    public static void print144(List<MBean> mainList) {
        System.out.println();
        System.out.println("#####################解析1.44比赛###################");
        System.out.println();
        for (int i = 0; i < mainList.size(); i ++) {
            int count = 0;
            for (int j = 0; j < mainList.get(i).getoList().size(); j ++) {
                if (mainList.get(i).getoList().get(j).endS.equals("1.44") ||
                        mainList.get(i).getoList().get(j).endF.equals("1.44")) {
                    count = count + 1;
                }
            }

            if (count >= 2) {
                System.out.println(mainList.get(i));
            }
        }
        System.out.println();
        System.out.println("#####################解析1.44比赛###################");
        System.out.println();
    }

    /**
     * 解析下盘低水比赛
     * @param mainList
     */
    public static void printDown(List<MBean> mainList) {
        System.out.println();
        System.out.println("#####################解析下盘低水比赛###################");
        System.out.println();
        for (int i = 0; i < mainList.size(); i ++) {
            int count = 0;
            for (int j = 0; j < mainList.get(i).getoList().size(); j ++) {
                if (mainList.get(i).getoList().get(j).endS.equals("1.44") ||
                        mainList.get(i).getoList().get(j).endF.equals("1.44")) {
                    count = count + 1;
                }
            }

            if (count >= 2) {
                System.out.println(mainList.get(i));
            }
        }
        System.out.println();
        System.out.println("#####################解析下盘低水比赛###################");
        System.out.println();
    }
}
