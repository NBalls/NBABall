package ball.drive.com.nbaball.network

import ball.drive.com.nbaball.entity.TeamGroup
import com.google.gson.Gson
import rx.Observable

/**
 * Created by aaron on 2018/4/9.
 */
class APIClient {
    private val netClient: NetClient = NetClient()

    fun requestTeamGroup(): Observable<TeamGroup> {
        return netClient.doGetRequest(URLClient.TEAM_GROUP_URL, HashMap())
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), TeamGroup::class.java)
                }
    }
}