package ball.drive.com.nbaball.qdog.bean;

/**
 * Created by aaron on 2018/8/6.
 * 大小数据
 */
public class BBean {

    public String company = "";
    public String startB = "";
    public String startPan = "";
    public String startS = "";
    public String endB = "";
    public String endPan = "";
    public String endS = "";

    @Override
    public String toString() {
        return company + " " + startB + "  " + startPan + "  " + startS
                 + "  " + endB + "  " + endPan + "  " + endS + "\n";
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStartB() {
        return startB;
    }

    public void setStartB(String startB) {
        this.startB = startB;
    }

    public String getStartPan() {
        return startPan;
    }

    public void setStartPan(String startPan) {
        this.startPan = startPan;
    }

    public String getStartS() {
        return startS;
    }

    public void setStartS(String startS) {
        this.startS = startS;
    }

    public String getEndB() {
        return endB;
    }

    public void setEndB(String endB) {
        this.endB = endB;
    }

    public String getEndPan() {
        return endPan;
    }

    public void setEndPan(String endPan) {
        this.endPan = endPan;
    }

    public String getEndS() {
        return endS;
    }

    public void setEndS(String endS) {
        this.endS = endS;
    }
}
