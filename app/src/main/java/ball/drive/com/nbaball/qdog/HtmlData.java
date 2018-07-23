package ball.drive.com.nbaball.qdog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ball.drive.com.nbaball.qdog.bean.MBean;

/**
 * Created by aaron on 2018/7/24.
 */

public class HtmlData {
    public static File sourceFile = new File("/Users/aaron/Desktop/zucai/templete.html");
    public static File resultFile = new File("/Users/aaron/Desktop/zucai/index.html");

    public static void htmlData(List<MBean> mainList) throws Exception {
        StringBuffer sourceSb = readSourceFile();
        String result = replaceHTML(mainList, sourceSb);
        writeResultFile(result);
    }

    private static void writeResultFile(String result) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(resultFile));
        bw.write(result);
        bw.close();
    }

    private static StringBuffer readSourceFile() throws Exception {
        StringBuffer sourceSb = new StringBuffer();
        BufferedReader br = new BufferedReader(new FileReader(sourceFile));
        String temp = "";
        while((temp = br.readLine()) != null) {
            sourceSb.append(temp);
        }
        br.close();

        return sourceSb;
    }

    private static String replaceHTML(List<MBean> mainList, StringBuffer sourceSb) {
        StringBuffer tempSb = new StringBuffer();
        for (int i = 0; i < mainList.size(); i++) {
            if (mainList.get(i).getyList().size() != mainList.get(i).getoList().size()) {
                return "";
            }
            tempSb.append("<table border='0' cellpadding='5' cellspacing='0'>\n" +
                    "\t<tr bgcolor='#66aa66' bgcolor='0'>\n" +
                    "\t\t<td align='center' width='60'>")
                    .append(parseNumber(i + 1))
                    .append("</td><td align='center' width='60'>")
                    .append(mainList.get(i).getStatus())
                    .append("</td><td align='center' width='60'>")
                    .append(mainList.get(i).getLiansai())
                    .append("</td><td align='center' width='60'>")
                    .append(mainList.get(i).getTime())
                    .append("</td>\n" +
                            "\t\t<td align='center' width='60'>")
                    .append(mainList.get(i).getZudui())
                    .append("</td><td align='center' width='60'>")
                    .append(mainList.get(i).getKedui())
                    .append("</td>\n" +
                            "\t\t<td align='center' width='60'>")
                    .append("析")
                    .append("</td><td align='center' width='60'>")
                    .append("大")
                    .append("</td>\n" +
                            "\t\t<td align='center' width='60'>")
                    .append("欧")
                    .append("</td><td align='center' width='60'>")
                    .append("亚")
                    .append("</td>\n" +
                            "\t\t<td></td>\n" +
                            "\t\t<td></td>\n" +
                            "\t\t<td></td>\n" +
                            "\t\t<td></td>\n" +
                            "\t</tr>");

            for (int j = 0; j < mainList.get(i).getyList().size(); j++) {
                tempSb.append("<tr bgcolor='#EEEEEE'>\n" +
                        "\t\t<td align='center' width='100'>")
                        .append(mainList.get(i).getyList().get(j).getCompany())
                        .append("</td><td align='center' width='60'>")
                        .append(mainList.get(i).getyList().get(j).getStartZRate())
                        .append("</td><td align='center' width='100'>")
                        .append(mainList.get(i).getyList().get(j).getStartPan())
                        .append("</td><td align='center' width='60'>")
                        .append(mainList.get(i).getyList().get(j).getStartKRate())
                        .append("</td><td align='center' width='60'>")
                        .append(mainList.get(i).getyList().get(j).getEndZRate())
                        .append("</td><td align='center' width='100'>")
                        .append(mainList.get(i).getyList().get(j).getEndPan())
                        .append("</td><td align='center' width='60'>")
                        .append(mainList.get(i).getyList().get(j).getEndKRate())
                        .append("</td>\n" +
                                "\t\t<td align='center' width='60'>")
                        .append(mainList.get(i).getoList().get(j).getCompany())
                        .append("</td><td align='center' width='60'>")
                        .append(mainList.get(i).getoList().get(j).getStartS())
                        .append("</td><td align='center' width='60'>")
                        .append(mainList.get(i).getoList().get(j).getStartP())
                        .append("</td><td align='center' width='60'>")
                        .append(mainList.get(i).getoList().get(j).getStartF())
                        .append("</td><td align='center' width='60'>")
                        .append(mainList.get(i).getoList().get(j).getEndS())
                        .append("</td><td align='center' width='60'>")
                        .append(mainList.get(i).getoList().get(j).getEndP())
                        .append("</td><td align='center' width='60'>")
                        .append(mainList.get(i).getoList().get(j).getEndF())
                        .append("</td>\n" +
                                "\t</tr>");
            }
        }



        return sourceSb.toString().replace("${today}", parseSimpleData())
                .replace("${matchCount}", mainList.size() + "")
                .replace("${earlyTime}", "16:00")
                .replace("${completeCount}", "8")
                .replace("${playingCount}", "8")
                .replace("${nostartCount}", "8")
                .replace("${table}", tempSb.toString());
    }

    private static String parseNumber(int i) {
        if (i < 10) {
            return "00" + i;
        } else if (i < 100) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }

    private static String parseSimpleData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}

















