package ball.drive.com.nbaball.qdog.bean;

/**
 * Created by aaron on 2018/8/3.
 * 近期比赛
 */
public class RBean {
    public String nresult = "";
    public String rresult = "";
    public String bresult = "";

    @Override
    public String toString() {
        return "结果：" + getNresult() + "  让球：" + getRresult() + "  大小：" + getBresult() + "\n";
    }

    public String getNresult() {
        return nresult;
    }

    public void setNresult(String nresult) {
        this.nresult = nresult;
    }

    public String getRresult() {
        return rresult;
    }

    public void setRresult(String rresult) {
        this.rresult = rresult;
    }

    public String getBresult() {
        return bresult;
    }

    public void setBresult(String bresult) {
        this.bresult = bresult;
    }
}
