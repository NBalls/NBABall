package ball.drive.com.nbaball.qiutan;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ball.drive.com.nbaball.TanCompleteListener;
import ball.drive.com.nbaball.TanListener;
import ball.drive.com.nbaball.network.APIClient;
import ball.drive.com.nbaball.qdog.util.ParserUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by aaron on 2018/7/23.
 * 解析数据
 */
public class MClass {
    private static APIClient apiClient = new APIClient();
    private static List<MainBean> mDataList = new ArrayList();
    public static TanCompleteListener tanCompleteListener = null;
    public static int count = 0;
    public static boolean isFinish = false;

    /**
     * 解析比赛数据
     */
    public static void parseMainData(Document doc) {
        count = 0;
        isFinish = false;
        final Elements elements = doc.body().getElementsByAttribute("index");
        final TanListener tanListener = new TanListener() {
            @Override
            public void onTanListener() {
                if (mDataList.size() >= count - 3 && isFinish) {
                    if (tanCompleteListener != null) {
                        tanCompleteListener.onTanCompleteListener(mDataList);
                    }
                }
            }
        };
        for (int i = 0; i < Math.min(elements.size(), 200); i ++) {
            // 亚盘欧指数据不为空，状态为空
            if (!TextUtils.isEmpty(elements.get(i).child(8).text())
                    && TextUtils.isEmpty(elements.get(i).child(3).text())) {
                count = count + 1;
                Log.i("MClass", "开始解析第" + (i + 1) + "条数据..........");
                MainBean mainBean = new MainBean();
                String ids = elements.get(i).attr("id")
                        .substring(elements.get(i).attr("id").indexOf("_") + 1);
                String liansai = elements.get(i).child(1).text();
                String time = elements.get(i).child(2).text();
                String status = elements.get(i).child(3).text();
                if (status.equals("")) {
                    status = "未开始";
                }
                String zhu = elements.get(i).child(4).child(3).text();
                String bifen = elements.get(i).child(5).text();
                if (bifen.trim().equals("-")) {
                    bifen = "0:0";
                }
                String ke = elements.get(i).child(6).child(0).text();
                mainBean.setId(ids);
                mainBean.setLiansai(liansai);
                mainBean.setTime(time);
                mainBean.setStatus(status);
                mainBean.setZhu(zhu);
                mainBean.setBifen(bifen);
                mainBean.setKe(ke);
                parseYOData(mainBean, tanListener);
            } else {
                Log.i("MClass", "开始解析第" + (i + 1) + "条数据，数据不全舍弃.......");
            }
            if (i >= Math.min(elements.size(), 200) - 1) {
                isFinish = true;
            }
        }
    }

    /**
     * 解析亚盘数据
     * @param mainBean
     */
    public static void parseYOData(final MainBean mainBean, final TanListener tanListener) {
        try {
            Document doc = Jsoup.connect(mainBean.getYaUrl()).get();
            final Element tableElement = doc.getElementById("odds");
            Elements trElement = tableElement.getElementsByTag("tr");
            List<YBean> mList = new ArrayList();
            for (int i = 0; i < trElement.size(); i++) {
                if (trElement.get(i).attr("class") == "" &&
                        trElement.get(i).child(2).attr("title") != "") {
                    YBean yBean = new YBean();
                    yBean.setCompany(trElement.get(i).child(0).text().trim());
                    yBean.setStartTime(trElement.get(i).child(2).attr("title"));
                    yBean.setStartZRate(trElement.get(i).child(2).text());
                    yBean.setStartPan(ParserUtil.changePan(trElement.get(i).child(3).text()));
                    yBean.setStartKRate(trElement.get(i).child(4).text());
                    yBean.setEndZRate(trElement.get(i).child(5).text());
                    yBean.setEndPan(ParserUtil.changePan(trElement.get(i).child(6).text()));
                    yBean.setEndKRate(trElement.get(i).child(7).text());
                    mList.add(yBean);
                }
            }
            mainBean.setyList(mList);

            apiClient.getNetClient().doGetRequestHtml(mainBean.getOuUrl(), new HashMap<String, String>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            String[] result = s.split("\n");
                            String ouData = result[26];
                            if (ouData.contains("\"") && ouData.contains("\"")) {
                                String ouData1 = ouData.substring(ouData.indexOf("\"") + 1, ouData.lastIndexOf("\""));
                                String[] ouData2 = ouData1.split("\",\"");
                                List<OBean> oList = new ArrayList();
                                for (int i = 0; i < ouData2.length; i ++) {
                                    String[] ouData3 = ouData2[i].split("\\|");
                                    String company = ouData3[21];
                                    String startS = ouData3[3];
                                    String startP = ouData3[4];
                                    String startF = ouData3[5];
                                    String endS = ouData3[10];
                                    String endP = ouData3[11];
                                    String endF = ouData3[12];
                                    OBean oBean = new OBean();
                                    oBean.setCompany(company);
                                    oBean.setEndF(endF);
                                    oBean.setEndP(endP);
                                    oBean.setEndS(endS);
                                    oBean.setStartF(startF);
                                    oBean.setStartP(startP);
                                    oBean.setStartS(startS);
                                    oList.add(oBean);
                                }

                                mainBean.setoList(oList);
                                mDataList.add(mainBean);
                                tanListener.onTanListener();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<RBean> parseResult(Document doc) {
        Elements elements = doc.body().child(0).getElementsByAttribute("infoid");
        List mList = new ArrayList<RBean>();
        for (int i = 0; i < elements.size(); i ++) {
            RBean rbean = new RBean();
            rbean.setLiansai(elements.get(i).child(0).text());
            rbean.setDate(elements.get(i).child(1).text());
            rbean.setStatus(elements.get(i).child(2).text());
            rbean.setZhudui(elements.get(i).child(3).text());
            rbean.setPoints(elements.get(i).child(4).text());
            rbean.setKedui(elements.get(i).child(5).text());
            mList.add(rbean);
        }

        return mList;
    }

    public static List<MainBean> parse144(List<MainBean> mDataList) {
        List<MainBean> mList = new ArrayList();
        for (int i = 0; i < mDataList.size(); i ++) {
            for (int j = 0; j < mDataList.get(i).getoList().size(); j ++) {
                if (mDataList.get(i).getoList().get(j).company.contains("威廉希尔")) {
                    if (mDataList.get(i).getoList().get(j).endS.equals("1.44")
                            || mDataList.get(i).getoList().get(j).endF.equals("1.44")) {
                        mList.add(mDataList.get(i));
                    }
                }
            }
        }

        return mList;
    }

    public static List<MainBean> parse165(List<MainBean> mDataList) {
        List<MainBean> mList = new ArrayList();
        for (int i = 0; i < mDataList.size(); i ++) {
            for (int j = 0; j < mDataList.get(i).getoList().size(); j ++) {
                if (mDataList.get(i).getoList().get(j).company.contains("威廉希尔")) {
                    if (mDataList.get(i).getoList().get(j).startS.equals("1.65")
                            || mDataList.get(i).getoList().get(j).startF.equals("1.65")
                            || mDataList.get(i).getoList().get(j).endS.equals("1.65")
                            || mDataList.get(i).getoList().get(j).endF.equals("1.65")) {
                        mList.add(mDataList.get(i));
                    }
                }
            }
        }

        return mList;
    }

    public static List<MainBean> parseCOver(List<MainBean> mDataList) {
        List<MainBean> mList = new ArrayList();
        for (int i = 0; i < mDataList.size(); i ++) {
            for (int j = 0; j < mDataList.get(i).getyList().size(); j ++) {
                if (mDataList.get(i).getyList().get(j).company.contains("易胜博")) {
                    float endPan = Float.valueOf(mDataList.get(i).getyList().get(j).endPan);
                    float startPan = Float.valueOf(mDataList.get(i).getyList().get(j).startPan);
                    if (Math.abs(startPan - endPan) >= 0.5) {
                        mList.add(mDataList.get(i));
                    }
                }
            }
        }

        return mList;
    }

    public static List<MainBean> parseDown(List<MainBean> mDataList) {
        List<MainBean> mList = new ArrayList();
        for (int i = 0; i < mDataList.size(); i ++) {
            int count = 0;
            for (int j = 0; j < mDataList.get(i).getyList().size(); j ++) {
                if (mDataList.get(i).getyList().get(j).company.contains("易胜博")
                        || mDataList.get(i).getyList().get(j).company.contains("365")
                        || mDataList.get(i).getyList().get(j).company.contains("Crown")) {
                    float endPan = Float.valueOf(mDataList.get(i).getyList().get(j).endPan);
                    float startPan = Float.valueOf(mDataList.get(i).getyList().get(j).startPan);
                    if (endPan > 0 && endPan < 1 && startPan - endPan == 0f && Float.valueOf(mDataList.get(i).getyList().get(j).startKRate) -
                            Float.valueOf(mDataList.get(i).getyList().get(j).endKRate) > 0) {
                        count = count + 1;
                    } else if (endPan < 0 && endPan > -1 && startPan - endPan == 0f && Float.valueOf(mDataList.get(i).getyList().get(j).endZRate) -
                            Float.valueOf(mDataList.get(i).getyList().get(j).startZRate) < 0) {
                        count = count + 1;
                    }
                }
            }

            if (count >= 3) {
                mList.add(mDataList.get(i));
            }
        }

        return mList;
    }

    public static List<MainBean> parseZero(List<MainBean> mDataList) {
        List<MainBean> mList = new ArrayList();
        for (int i = 0; i < mDataList.size(); i ++) {
            int count = 0;
            for (int j = 0; j < mDataList.get(i).getyList().size(); j ++) {
                if (mDataList.get(i).getyList().get(j).company.contains("易胜博")
                        || mDataList.get(i).getyList().get(j).company.contains("365")
                        || mDataList.get(i).getyList().get(j).company.contains("Crown")) {
                    float endPan = Float.valueOf(mDataList.get(i).getyList().get(j).endPan);
                    float startPan = Float.valueOf(mDataList.get(i).getyList().get(j).startPan);
                    if (startPan - endPan == 0f && startPan == 0f) {
                        count = count + 1;
                    }
                }
            }

            if (count >= 3) {
                mList.add(mDataList.get(i));
            }
        }

        return mList;
    }


    public static List<MainBean> parseCut(List<MainBean> mDataList) {
        List<MainBean> mList = new ArrayList();
        for (int i = 0; i < mDataList.size(); i ++) {
            int count = 0;
            for (int j = 0; j < mDataList.get(i).getyList().size(); j ++) {
                if (mDataList.get(i).getyList().get(j).company.contains("易胜博")
                        || mDataList.get(i).getyList().get(j).company.contains("365")
                        || mDataList.get(i).getyList().get(j).company.contains("Crown")) {
                    float endPan = Float.valueOf(mDataList.get(i).getyList().get(j).endPan);
                    float startPan = Float.valueOf(mDataList.get(i).getyList().get(j).startPan);
                    if (endPan >= 0 && endPan <= 1 && startPan >= 0 && startPan <= 1) {
                        if (endPan - startPan < 0) {
                            float endRate = Float.valueOf(mDataList.get(i).getyList().get(j).endKRate);
                            float startRate = Float.valueOf(mDataList.get(i).getyList().get(j).startKRate);
                            if (endRate - startRate < 0) {
                                count = count + 1;
                            }
                        }
                    } else if (endPan <= 0 && endPan >= -1 && startPan <= 0 && startPan >= -1) {
                        if (endPan - startPan > 0) {
                            float endRate = Float.valueOf(mDataList.get(i).getyList().get(j).endZRate);
                            float startRate = Float.valueOf(mDataList.get(i).getyList().get(j).startZRate);
                            if (endRate - startRate < 0) {
                                count = count + 1;
                            }
                        }
                    }
                }
            }

            if (count >= 2) {
                mList.add(mDataList.get(i));
            }
        }

        return mList;
    }
}
