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
    public static TanCompleteListener tanCompleteListener = null;
    private static APIClient apiClient = new APIClient();
    private static ArrayList mDataList = new ArrayList();
    private static boolean isFinish = false;
    private static int count = 0;

    /**
     * 解析比赛数据
     */
    public static void parseMainData(Document doc) {
        count = 0;
        isFinish = false;
        mDataList.clear();
        final Elements elements = doc.body().getElementsByAttribute("index");
        final TanListener tanListener = new TanListener() {
            @Override
            public void onTanListener() {
                Log.i("MClass", "######### size:" + mDataList.size() + "   count:" + (count - 3));
                if (mDataList.size() >= count - 3 && isFinish) {
                    if (tanCompleteListener != null) {
                        tanCompleteListener.onTanCompleteListener(mDataList);
                    }
                }
            }
        };
        for (int i = 3; i < Math.min(elements.size(), 200); i ++) {
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

            Log.i("MClass", "ouUrl:" + mainBean.getOuUrl());
            apiClient.getNetClient().doGetRequestHtml(mainBean.getOuUrl(), new HashMap<String, String>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            String[] result = s.split("\n");
                            String ouData = result[28];
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

    public static void parseRData(MainBean mainBean) {
        // TODO 解析分析数据（对往比赛，近期比赛）
        apiClient.getNetClient().doGetRequestHtml(mainBean.getAUrl(), new HashMap<String, String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("MClass", s);
                        // 解析亚盘数据
                        /*String paramYStr1 = s.substring(s.indexOf("Vs_hOdds=[["));
                        String paramYStr2 = paramYStr1.substring(11, paramYStr1.indexOf("]];"));
                        String[] arrYStr1 = paramYStr2.split("],\\[");
                        for (int i = 0; i < arrYStr1.length; i ++) {
                            // Log.i("MClass", arrYStr1[i] + "");
                        }
                        // 解析欧盘数据
                        String paramOStr1 = s.substring(s.indexOf("Vs_eOdds = [["));
                        String paramOStr2 = paramOStr1.substring(11, paramOStr1.indexOf("]];"));
                        String[] arrOStr1 = paramOStr2.split("],\\[");
                        for (int i = 0; i < arrOStr1.length; i ++) {
                            Log.i("MClass", arrOStr1[i] + "");
                        }


                        String subStr1 = s.substring(s.indexOf("v_data"));
                        String subStr2 = subStr1.substring(11, subStr1.indexOf("]];"));
                        String[] arrayStr1 = subStr2.split("],\\[");
                        String subStr3 = arrayStr1[0];
                        String[] arrayStr2 = subStr3.split(",");

                        NBean nBean = new NBean();
                        nBean.setDate(arrayStr2[0]);
                        String kedui = arrayStr2[7];
                        nBean.setKedui(kedui.substring(kedui.indexOf(">") + 1, kedui.indexOf("<")));
                        nBean.setKePoint(arrayStr2[9]);
                        nBean.setKePai(kedui.substring(kedui.indexOf("：") + 1, kedui.indexOf("\">")).trim());
                        nBean.setLiansai(arrayStr2[2]);
                        String zhudui = arrayStr2[5];
                        nBean.setZhudui(zhudui.substring(zhudui.indexOf(">") + 1, zhudui.indexOf("<")));
                        nBean.setZhuPoint(arrayStr2[8]);
                        nBean.setZhuPai(zhudui.substring(zhudui.indexOf("：") + 1, zhudui.indexOf("\">")).trim());
                        nBean.setIds(arrayStr2[15]);
                        // getY(arrYStr1, nBean.getIds());
                        String temp1 = s.substring(s.indexOf("[" + nBean.getIds() + ",8"));
                        String temp2 = temp1.substring(0, temp1.indexOf("],\\["));
                        Log.i("MClass", temp2);*/
                    }
                });
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
}
