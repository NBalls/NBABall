package ball.drive.com.nbaball.qiutan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron on 2018/9/13.
 * 解析数据
 */
public class PrintClass {

    public static List<MainBean> parse144(List<MainBean> mDataList) {
        ArrayList<MainBean> mList = new ArrayList();
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
                    if (Math.abs(startPan - endPan) >= 0.25 && Math.abs(endPan) < 1.75) {
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
                if (isCompany(mDataList.get(i).getyList().get(j).company)) {
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
                if (isCompany(mDataList.get(i).getyList().get(j).company)) {
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
                if (isCompany(mDataList.get(i).getyList().get(j).company)) {
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

            if (count >= 1) {
                mList.add(mDataList.get(i));
            }
        }

        return mList;
    }

    public static List<MainBean> parse025(List<MainBean> mDataList) {
        List<MainBean> mList = new ArrayList();
        for (int i = 0; i < mDataList.size(); i ++) {
            int count = 0;
            for (int j = 0; j < mDataList.get(i).getyList().size(); j ++) {
                if (isCompany(mDataList.get(i).getyList().get(j).company)) {
                    float endPan = Float.valueOf(mDataList.get(i).getyList().get(j).endPan);
                    float startPan = Float.valueOf(mDataList.get(i).getyList().get(j).startPan);
                    if (startPan == 0 && (endPan == -0.25 || endPan == 0.25)) {
                        count = count + 1;
                    }
                }
            }

            if (count >= 2) {
                mList.add(mDataList.get(i));
            }
        }

        return mList;
    }

    public static List<MainBean> parseDeep(List<MainBean> mDataList) {
        List<MainBean> mList = new ArrayList();
        for (int i = 0; i < mDataList.size(); i ++) {
            int count = 0;
            for (int j = 0; j < mDataList.get(i).getyList().size(); j ++) {
                if (isCompany(mDataList.get(i).getyList().get(j).company)) {
                    float endPan = Float.valueOf(mDataList.get(i).getyList().get(j).endPan);
                    float startPan = Float.valueOf(mDataList.get(i).getyList().get(j).startPan);
                    if (Math.abs(startPan) >= 1 && Math.abs(endPan) - Math.abs(startPan) >= 0.25) {
                        count = count + 1;
                    }
                }
            }

            if (count >= 2) {
                mList.add(mDataList.get(i));
            }
        }

        return mList;
    }


    private static boolean isCompany(String company) {
        return company.contains("易胜博")
                || company.contains("365")
                || company.contains("Crown")
                || company.contains("威廉");
    }
}
