package ball.drive.com.nbaball.network

import ball.drive.com.nbaball.entity.*
import com.google.gson.Gson
import rx.Observable
import java.util.*

/**
 * Created by aaron on 2018/4/9.
 */
class APIClient {
    private val netClient: NetClient = NetClient()

    fun requestTeamGroup(): Observable<TeamGroup> {
        return netClient.doGetRequest(URLClient.TEAM_GROUP_URL, HashMap<String, String>())
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), TeamGroup::class.java)
                }
    }

    fun requestTeamStats(): Observable<TeamStats> {
        return netClient.doGetRequest(URLClient.TEAM_STATS_URL, HashMap<String, String>())
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), TeamStats::class.java)
                }
    }

    fun requestTeamStanding(): Observable<TeamStanding> {
        return netClient.doGetRequest(URLClient.TEAM_STANDING_URL, HashMap<String, String>())
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), TeamStanding::class.java)
                }
    }

    fun requestTeamLeader(): Observable<TeamLeader> {
        return netClient.doGetRequest(URLClient.TEAM_LEADER_URL, HashMap<String, String>())
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), TeamLeader::class.java)
                }
    }

    fun requestGameSchedule(): Observable<TeamSchedule> {
        return netClient.doGetRequest(URLClient.TEAM_SCHEDULE_URL, HashMap<String, String>())
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), TeamSchedule::class.java)
                }
    }
}