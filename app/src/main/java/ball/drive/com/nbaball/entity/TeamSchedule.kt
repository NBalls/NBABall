package ball.drive.com.nbaball.entity

import ball.drive.com.nbaball.entity.common.*
import java.util.*

/**
 * Created by aaron on 2018/4/10.
 */
class TeamSchedule {
    val context = CommonContext()
    val error = CommonError()
    val payload = TeamSchedulePayLoad()
    val timestamp = ""
}

class TeamSchedulePayLoad {
    val league = CommonLeague()
    val season = CommonSeason()
    val dates = ArrayList<TeamScheduleDate>()
    val utcMillis = ""
}

class TeamScheduleDate {
    val games = ArrayList<TeamScheduleGame>()
    val gameCount = ""
    val utcMillis = ""
}

class TeamScheduleGame {
    val profile = GameProFile()
    val boxscore = BoxScore()
    val urls = ArrayList<GameUrl>()
    val broadcasters = ArrayList<BroadCaster>()
    val homeTeam = GameTeam()
    val awayTeam = GameTeam()
}

class GameProFile {
    val arenaLocation = ""
    val arenaName = ""
    val awayTeamId = ""
    val gameId = ""
    val homeTeamId = ""
    val number = ""
    val scheduleCode = ""
    val seasonType = ""
    val sequence = ""
    val utcMillis = ""
}

class BoxScore {
    val attendance = ""
    val awayScore = 0
    val gameLength = ""
    val homeScore = 0
    val officialsDisplayName1 = ""
    val officialsDisplayName2 = ""
    val officialsDisplayName3 = ""
    val period = ""
    val periodClock = ""
    val status = ""
    val statusDesc = ""
    val ties = ""
}

class GameUrl {
    val displayText = ""
    val type = ""
    val value = ""
}

class BroadCaster {
    val id = ""
    val media = ""
    val name = ""
    val range = ""
    val type = ""
    val url = ""
}

class GameTeam {
    val profile = CommonProfile()
    val matchup = ""
}