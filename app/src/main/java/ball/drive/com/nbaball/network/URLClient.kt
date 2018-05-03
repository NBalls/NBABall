package ball.drive.com.nbaball.network

/**
 * Created by aaron on 2018/4/9.
 */
class URLClient {
    companion object {
        val TEAM_GROUP_URL = "http://china.nba.com/static/data/league/conferenceteamlist.json"

        val TEAM_STATS_URL = "http://china.nba.com/static/data/league/teamstats_All_All_2017_2.json"

        /**
         * 球队比赛信息
         */
        val TEAM_STANDING_URL = "http://china.nba.com/static/data/team/standing_raptors.json"

        val TEAM_LEADER_URL = "http://china.nba.com/static/data/team/leader_raptors.json"

        val TEAM_SCHEDULE_URL = "http://china.nba.com/static/data/season/schedule_7.json"



        val GAME_DATA_URL = "http://live.titan007.com/"

        val GAME_TRADE_DATA_URL = "http://trade.500.com/jczq/"
        /**
         * 数据分析：
         * http://trade.500.com/static/public/jczq/xml/odds/odds.xml
         *
         * <match id="121874" processdate="2018-05-07" processname="1001">
         * ----------欧赔3.69、3.36、1.94
         * <europe avg="3.69,3.36,1.94" wl="3.70,3.50,1.95" am="3.65,3.30,1.90" lb="3.80,3.30,1.95" bet365="3.75,3.50,1.95" hg="3.85,3.50,1.98"/>
         * ----------亚盘贴水、盘口、贴水
         * <asian am="0.860,受半球,0.940" lb="" bet365="0.899,受半球,0.950" hg="0.920,受半球,0.980"/>
         * ----------赢率：胜率，平率，负率
         * <gl avg="25.02,27.45,47.53" wl="25.29,26.73,47.98" am="24.83,27.47,47.70" lb="24.39,28.08,47.53" bet365="25.03,26.82,48.14" hg="24.73,27.20,48.08"/>
         * <kl avg="0.92,0.92,0.92" wl="0.92,0.96,0.93" am="0.91,0.91,0.90" lb="0.95,0.91,0.93" bet365="0.94,0.96,0.93" hg="0.96,0.96,0.94"/>
         * <bq bet365="0.899,受半球,0.950" avg="0.867,受半球,0.956"/>
         * <rq bet365="1.80,3.75,3.39" wl="1.73,3.70,3.60" avg="1.77,3.62,3.53"/>
         * <dxq bet365="1.04,2.5,0.77" am="0.92,2.5,0.78"/>
         * </match>
         */
    }
}