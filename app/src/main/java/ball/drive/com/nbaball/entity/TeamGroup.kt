package ball.drive.com.nbaball.entity

import ball.drive.com.nbaball.entity.common.*
import java.util.*

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
    val profile = CommonProfile()
}