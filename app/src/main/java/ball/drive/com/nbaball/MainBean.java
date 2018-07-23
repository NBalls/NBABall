package ball.drive.com.nbaball;

import java.util.ArrayList;
import java.util.List;

import ball.drive.com.nbaball.qiutan.OBean;
import ball.drive.com.nbaball.qiutan.YBean;

/**
 * Created by aaron on 2018/7/23.
 */

public class MainBean {

    public String id = "";
    public String liansai = "";
    public String time = "";
    public String status = "";
    public String zhu = "";
    public String ke = "";
    public String bifen = "";
    public List<YBean> yList = new ArrayList();
    public List<OBean> oList = new ArrayList();

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(liansai + " " + time + "  " + status + "  " + bifen + "  " + zhu + "VS" + ke + "\n");

        sb.append(getYaUrl() + "\n");
        for (int i = 0; i < yList.size(); i ++) {
            sb.append(yList.get(i).toString());
        }

        for (int i = 0; i < oList.size(); i ++) {
            sb.append(oList.get(i).toString());
        }
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZhu() {
        return zhu;
    }

    public void setZhu(String zhu) {
        this.zhu = zhu;
    }

    public String getKe() {
        return ke;
    }

    public void setKe(String ke) {
        this.ke = ke;
    }

    public String getBifen() {
        return bifen;
    }

    public void setBifen(String bifen) {
        this.bifen = bifen;
    }

    public String getYaUrl() {
        return "http://vip.win007.com/AsianOdds_n.aspx?id=" + id;
    }

    public String getOuUrl() {
        return "http://op1.win007.com/oddslist/" + id + ".htm";
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
}