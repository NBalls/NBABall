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
    }
}