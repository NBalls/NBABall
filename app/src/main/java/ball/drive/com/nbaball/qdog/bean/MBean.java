package ball.drive.com.nbaball.qdog.bean;

import java.util.ArrayList;
import java.util.List;

import ball.drive.com.nbaball.qdog.bean.OBean;
import ball.drive.com.nbaball.qdog.bean.RBean;
import ball.drive.com.nbaball.qdog.bean.YBean;

/**
 * Created by aaron on 2018/7/24.
 * 主体比赛数据
 */
public class MBean {
    public String id = "";
    public String liansai = "";
    public String status = "";
    public String time = "";
    public String zudui = "";
    public String kedui = "";
    public String zhuPoint = "";
    public String kePoint = "";
    public String zhuUrl = "";
    public String keUrl = "";
    public String analyseUrl = "";
    public String asiaUrl = "";
    public String ouUrl = "";
    public String bigUrl = "";
    public String zhuRank = "";
    public String keRank = "";
    public List<YBean> yList = new ArrayList();// 亚盘数据
    public List<OBean> oList = new ArrayList();// 欧指数据
    public List<BBean> bList = new ArrayList();// 大小数据
    public List<FBean> fList = new ArrayList();// 对往战绩
    public List<RBean> zList = new ArrayList();// 主队近期战绩
    public List<RBean> kList = new ArrayList();// 客队近期战绩

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(liansai + "  " + time + "  " + zudui + "VS" + kedui + " " + zhuPoint + " : " + kePoint + "\n");
        sb.append("主队URL：" + getZhuUrl() + "\n");
        sb.append("客队URL：" + getKeUrl() + "\n");
        sb.append("析URL：" + getAnalyseUrl() + "\n");
        sb.append("亚URL：" + getAsiaUrl() + "\n");
        sb.append("大URL：" + getBigUrl() + "\n");
        sb.append("欧URL：" + getOUrl() + "\n");
        sb.append("------ 亚盘数据 ------\n");
        for (int i = 0; i < yList.size(); i ++) {
            sb.append(yList.get(i));
        }
        sb.append("------ 欧指数据 ------\n");
        for (int i = 0; i < oList.size(); i ++) {
            sb.append(oList.get(i));
        }
        sb.append("------ 大小数据 ------\n");
        for (int i = 0; i < bList.size(); i ++) {
            sb.append(bList.get(i));
        }

        sb.append("两队比赛历史：\n");
        for (int i = 0; i < fList.size(); i ++) {
            sb.append(fList.get(i));
        }

        sb.append(getZudui()).append("近期比赛：\n");
        for (int i = 0; i < zList.size(); i ++) {
            sb.append(zList.get(i));
        }

        sb.append(getKedui()).append("近期比赛：\n");
        for (int i = 0; i < kList.size(); i ++) {
            sb.append(kList.get(i));
        }
        return sb.toString();
    }

    public String getLiansai() {
        return liansai;
    }

    public void setLiansai(String liansai) {
        this.liansai = liansai;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getZudui() {
        return zudui;
    }

    public void setZudui(String zudui) {
        this.zudui = zudui;
    }

    public String getKedui() {
        return kedui;
    }

    public void setKedui(String kedui) {
        this.kedui = kedui;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYUrl() {
        return "https://www.liaogou168.com/match/odd/asia/" + id;
    }

    public String getOUrl() {
        return "https://www.liaogou168.com/match/odd/ou/" + id;
    }

    public List<YBean> getyList() {
        return yList;
    }

    public void setyList(List<YBean> yList) {
        this.yList = yList;
    }

    public List<OBean> getoList() {
        return oList;
    }

    public void setoList(List<OBean> oList) {
        this.oList = oList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZhuPoint() {
        return zhuPoint;
    }

    public void setZhuPoint(String zhuPoint) {
        this.zhuPoint = zhuPoint;
    }

    public String getKePoint() {
        return kePoint;
    }

    public void setKePoint(String kePoint) {
        this.kePoint = kePoint;
    }

    public String getZhuUrl() {
        return "https://www.liaogou168.com" + zhuUrl;
    }

    public void setZhuUrl(String zhuUrl) {
        this.zhuUrl = zhuUrl;
    }

    public String getKeUrl() {
        return "https://www.liaogou168.com" + keUrl;
    }

    public void setKeUrl(String keUrl) {
        this.keUrl = keUrl;
    }

    public String getAnalyseUrl() {
        return "https://www.liaogou168.com" + analyseUrl;
    }

    public void setAnalyseUrl(String analyseUrl) {
        this.analyseUrl = analyseUrl;
    }

    public String getAsiaUrl() {
        return "https://www.liaogou168.com" + asiaUrl;
    }

    public void setAsiaUrl(String asiaUrl) {
        this.asiaUrl = asiaUrl;
    }

    public String getOuUrl() {
        return "https://www.liaogou168.com" + ouUrl;
    }

    public void setOuUrl(String ouUrl) {
        this.ouUrl = ouUrl;
    }

    public String getBigUrl() {
        return bigUrl;
    }

    public void setBigUrl(String bigUrl) {
        this.bigUrl = bigUrl;
    }

    public List<RBean> getzList() {
        return zList;
    }

    public void setzList(List<RBean> zList) {
        this.zList = zList;
    }

    public List<RBean> getkList() {
        return kList;
    }

    public void setkList(List<RBean> kList) {
        this.kList = kList;
    }

    public List<BBean> getbList() {
        return bList;
    }

    public void setbList(List<BBean> bList) {
        this.bList = bList;
    }

    public List<FBean> getfList() {
        return fList;
    }

    public void setfList(List<FBean> fList) {
        this.fList = fList;
    }

    public String getZhuRank() {
        return zhuRank;
    }

    public void setZhuRank(String zhuRank) {
        this.zhuRank = zhuRank;
    }

    public String getKeRank() {
        return keRank;
    }

    public void setKeRank(String keRank) {
        this.keRank = keRank;
    }
}
