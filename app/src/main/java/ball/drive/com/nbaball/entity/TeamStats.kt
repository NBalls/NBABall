package ball.drive.com.nbaball.entity

import ball.drive.com.nbaball.entity.common.*
import java.util.*

/**
 * Created by aaron on 2018/4/9.
 */
class TeamStats {
    val context = CommonContext()
    val error = CommonError()
    val payload = PayloadStats()
    val timestamp = ""
}

class PayloadStats {
    val league = CommonLeague()
    val season = CommonSeason()
    val inputParameters = ""
    val leagueSeasonAverage = StatsAverage()
    val teams = ArrayList<TeamsStats>()
}

class TeamsStats {
    val profile = CommonProfile()
    val statAverage = StatsAverage()
    val statTotal = StatsTotal()
    val standings = ""
}

class StatsAverage {
    val assistsPg = 0.0f
    val blocksPg = 0.0f
    val defRebsPg = 0.0f
    val efficiency = 0
    val fgaPg = 0.0f
    val fgmPg = 0.0f
    val fgpct = 0.0f
    val foulsPg = 0.0f
    val ftaPg = 0.0f
    val ftmPg = 0.0f
    val ftpct = 0.0f
    val games = 0
    val gamesStarted = ""
    val minsPg = 0.0f
    val offRebsPg = 0.0f
    val pointsPg = 0.0f
    val rebsPg = 0.0f
    val stealsPg = 0.0f
    val tpaPg = 0.0f
    val tpmPg = 0.0f
    val tppct = 0.0f
    val turnoversPg = 0.0f
}

class StatsTotal {
    val assists = 0
    val blocks = 0
    val defRebs = 0
    val fga = 0
    val fgm = 0
    val fgpct = 0.0f
    val fouls = 0
    val fta = 0
    val ftm = 0
    val ftpct = 0.0f
    val mins = 0
    val offRebs = 0
    val points = 0
    val rebs = 0
    val secs = 0
    val steals = 0
    val tpa = 0
    val tpm = 0
    val tppct = 0.0f
    val turnovers = 0
}