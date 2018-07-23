package ball.drive.com.nbaball.qiutan;

/**
 * Created by aaron on 2018/7/24.
 */

public class OBean {
    public String company = "";
    public String srate = "";
    public String prate = "";
    public String frate = "";

    @Override
    public String toString() {
        return company + " 凯利指数：" + srate + "  " + prate + "  " + frate + "\n";
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSrate() {
        return srate;
    }

    public void setSrate(String srate) {
        this.srate = srate;
    }

    public String getPrate() {
        return prate;
    }

    public void setPrate(String prate) {
        this.prate = prate;
    }

    public String getFrate() {
        return frate;
    }

    public void setFrate(String frate) {
        this.frate = frate;
    }
}
