package ball.drive.com.nbaball.qiutan;

/**
 * Created by aaron on 2018/9/5.
 */

public class RBean {
    public String liansai = "";
    public String date = "";
    public String status = "";
    public String zhudui = "";
    public String points = "";
    public String kedui = "";

    @Override
    public String toString() {
        return liansai + " " + date + " " + status + " " + zhudui + " " + points + " " + kedui;
    }

    public String getLiansai() {
        return liansai;
    }

    public void setLiansai(String liansai) {
        this.liansai = liansai;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZhudui() {
        return zhudui;
    }

    public void setZhudui(String zhudui) {
        this.zhudui = zhudui;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getKedui() {
        return kedui;
    }

    public void setKedui(String kedui) {
        this.kedui = kedui;
    }
}
