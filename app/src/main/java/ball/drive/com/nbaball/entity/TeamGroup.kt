package ball.drive.com.nbaball.entity

import ball.drive.com.nbaball.entity.common.CommonContext
import ball.drive.com.nbaball.entity.common.CommonError
import ball.drive.com.nbaball.entity.common.CommonLeague
import ball.drive.com.nbaball.entity.common.CommonSeason

/**
 * Created by aaron on 2018/4/2.
 */
class TeamGroup {
    val context = CommonContext()
    val error = CommonError()
    val payload = Payload()
    val timestamp = ""
}

class Payload {
    val league = CommonLeague()
    val season = CommonSeason()
    val listGroups = ArrayList<ListGroup>()
    val grouping = ""
}

class ListGroup {
    val teams = ArrayList<Team>()
    val conference = ""
    val division = ""
}

class Team {
    val profile = Profile()
}

class Profile {
    val abbr: String = ""
    val city: String = ""
    val cityEn: String = ""
    val code: String = ""
    val conference: String = ""
    val displayAbbr: String = ""
    val displayConference:  String = ""
    val division: String = ""
    val id: String = ""
    val isAllStarTeam: Boolean = false
    val isLeagueTeam: Boolean = false
    val leagueId: String = ""
    val name: String = ""
    val nameEn: String = ""
}