package ball.drive.com.nbaball.qdog.bean;

/**
 * Created by aaron on 2018/7/24.
 * 欧指数据
 */
public class OBean {
    public String company = "";
    public String startS = "";
    public String startP = "";
    public String startF = "";
    public String endS = "";
    public String endP = "";
    public String endF = "";

    @Override
    public String toString() {
        return company + " " + startS + " " + startP + " " + startF
                + " " + endS + " " + endP + " " + endP + "\n";
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStartS() {
        return startS;
    }

    public void setStartS(String startS) {
        this.startS = startS;
    }

    public String getStartP() {
        return startP;
    }

    public void setStartP(String startP) {
        this.startP = startP;
    }

    public String getStartF() {
        return startF;
    }

    public void setStartF(String startF) {
        this.startF = startF;
    }

    public String getEndS() {
        return endS;
    }

    public void setEndS(String endS) {
        this.endS = endS;
    }

    public String getEndP() {
        return endP;
    }

    public void setEndP(String endP) {
        this.endP = endP;
    }

    public String getEndF() {
        return endF;
    }

    public void setEndF(String endF) {
        this.endF = endF;
    }
}
