package ball.drive.com.nbaball.entity

import ball.drive.com.nbaball.entity.common.*
import java.util.*

/**
 * Created by aaron on 2018/4/10.
 */
class TeamLeader {
    val context = CommonContext()
    val error = CommonError()
    val payload = TeamLeaderPayLoad()
    val timestamp = ""
}

class TeamLeaderPayLoad {
    val league = CommonLeague()
    val season = CommonSeason()
    val profile = CommonProfile()
    val pointLeader = Leader()
    val reboundLeader = Leader()
    val assistLeader = Leader()
    val blockLeader = Leader()
    val stealLeader = Leader()
    val threePtPctLeader = Leader()
    val fgPctLeader = Leader()
    val ftPctLeader = Leader()
    val minLeader = Leader()
}

class Leader {
    val players = ArrayList<Player>()
    val seasonType = ""
}

class Player {
    val profile = LeaderPlayer()
    val rank = ""
    val value = ""
}

class LeaderPlayer {
    val code = ""
    val country = ""
    val displayAffiliation = ""
    val displayName = ""
    val displayNameEn = ""
    val dob = ""
    val draftYear = ""
    val experience = ""
    val firstInitial = ""
    val firstName = ""
    val firstNameEn = ""
    val height = ""
    val jerseyNo = ""
    val lastName = ""
    val lastNameEn = ""
    val leagueId = ""
    val playerId = ""
    val position = ""
    val schoolType = ""
    val weight = ""
}