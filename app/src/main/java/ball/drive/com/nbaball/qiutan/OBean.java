package ball.drive.com.nbaball.qiutan;

/**
 * Created by aaron on 2018/7/24.
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
        return "company:" + company + "  startS:" + startS + "  startP:" + startP + "  startF:" + startF +
                "  endS:" + endS + "  endP:" + endP + "  endF:" + endF + "\n";
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
        if (startS.trim().equals("")) {
            this.startS = "0";
        }
    }

    public String getStartP() {
        return startP;
    }

    public void setStartP(String startP) {
        this.startP = startP;
        if (startP.trim().equals("")) {
            this.startP = "0";
        }
    }

    public String getStartF() {
        return startF;
    }

    public void setStartF(String startF) {
        this.startF = startF;
        if (startF.trim().equals("")) {
            this.startF = "0";
        }
    }

    public String getEndS() {
        return endS;
    }

    public void setEndS(String endS) {
        this.endS = endS;
        if (endS.trim().equals("")) {
            this.endS = "0";
        }
    }

    public String getEndP() {
        return endP;
    }

    public void setEndP(String endP) {
        this.endP = endP;
        if (endP.trim().equals("")) {
            this.endP = "0";
        }
    }

    public String getEndF() {
        return endF;
    }

    public void setEndF(String endF) {
        this.endF = endF;
        if (endF.trim().equals("")) {
            this.endF = "0";
        }
    }
}
