package ball.drive.com.nbaball.qiutan;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ball.drive.com.nbaball.MainBean;

/**
 * Created by aaron on 2018/7/23.
 */

public class MClass {

    public static String FILE_URL = "/Users/aaron/Desktop/haha/足球比分、" +
            "足球即时比分、比分直播、足球比分直播、足球直播-球探体育.html";
    public static String UTF_CODE = "UTF-8";
    public static String INDEX_TABLE = "table_live";
    public static String INDEX = "index";
    public static List<MainBean> mDataList = new ArrayList();

    public static void main(String[] args) {
        try {
            File file = new File(FILE_URL);
            Document doc = Jsoup.parse(file, UTF_CODE);
            parseMainData(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析比赛数据
     * @param doc
     */
    public static void parseMainData(Document doc) {
        Element tableElement = doc.getElementById(INDEX_TABLE);
        Elements trElement = tableElement.getElementsByAttribute(INDEX);
        mDataList.clear();
        for (int i = 0; i < Math.min(trElement.size(), 4); i ++) {
            MainBean mainBean = new MainBean();
            String ids = trElement.get(i).attr("id")
                    .substring(trElement.get(i).attr("id").indexOf("_") + 1);
            String liansai = trElement.get(i).child(1).text();
            String time = trElement.get(i).child(2).text();
            String status = trElement.get(i).child(3).text();
            String zhu = trElement.get(i).child(4).child(3).text();
            String bifen = trElement.get(i).child(5).text();
            String ke = trElement.get(i).child(6).child(0).text();
            mainBean.setId(ids);
            mainBean.setLiansai(liansai);
            mainBean.setTime(time);
            mainBean.setStatus(status);
            mainBean.setZhu(zhu);
            mainBean.setBifen(bifen);
            mainBean.setKe(ke);
            parseYData(mainBean);
        }
    }

    /**
     * 解析亚盘数据
     * @param mainBean
     */
    public static void parseYData(final MainBean mainBean) {
        try {
            Document doc = Jsoup.connect(mainBean.getYaUrl()).get();
            Element tableElement = doc.getElementById("odds");
            Elements trElement = tableElement.getElementsByTag("tr");
            List<YBean> mList = new ArrayList();
            for (int i = 0; i < trElement.size(); i++) {
                if (trElement.get(i).attr("class") == "" &&
                        trElement.get(i).child(2).attr("title") != "") {
                    YBean yBean = new YBean();
                    yBean.setCompany(trElement.get(i).child(0).text().trim());
                    yBean.setStartTime(trElement.get(i).child(2).attr("title"));
                    yBean.setStartZRate(trElement.get(i).child(2).text());
                    yBean.setStartPan(trElement.get(i).child(3).text());
                    yBean.setStartKRate(trElement.get(i).child(4).text());
                    yBean.setEndZRate(trElement.get(i).child(5).text());
                    yBean.setEndPan(trElement.get(i).child(6).text());
                    yBean.setEndKRate(trElement.get(i).child(7).text());
                    mList.add(yBean);
                }
            }
            // if (mList != null && mList.size() > 5) {
                mainBean.setyList(mList);
                parseOData(mainBean);
            // }
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    /**
     * 解析欧指数据
     */
    public static void parseOData(MainBean mainBean) {
        try {
            System.out.println(mainBean.getOuUrl());
            Document doc = Jsoup.connect(mainBean.getOuUrl()).get();
            Element tableElement = doc.body().getElementById("dataList");
            Element tElement = tableElement.getElementById("oddsList_tab");
            Element bodyElement = tElement.child(0);
            Elements trElement = bodyElement.children();

            if (trElement == null) {
                System.out.println("1111111111111");
                return;
            } else {
                System.out.println("2222222222222");
            }
            // Elements trElement = tableElement.getElementsByTag("tr");
            List<OBean> oBeanList = new ArrayList();
            for (int i = 0; i < trElement.size(); i ++) {
                OBean oBean = new OBean();
                oBean.setSrate(trElement.get(i).child(9).text());
                oBean.setPrate(trElement.get(i).child(10).text());
                oBean.setFrate(trElement.get(i).child(11).text());
                oBeanList.add(oBean);
            }
            mainBean.setoList(oBeanList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mDataList.add(mainBean);
        System.out.println(mainBean);
        System.out.println("#############################");
    }
}
