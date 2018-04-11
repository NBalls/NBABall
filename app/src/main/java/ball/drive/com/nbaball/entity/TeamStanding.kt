package ball.drive.com.nbaball.entity

import ball.drive.com.nbaball.entity.common.*

/**
 * Created by aaron on 2018/4/10.
 */
class TeamStanding {
    val context = CommonContext()
    val error = CommonError()
    val payload = TeamStandingPayLoad()
    val timestamp = ""
}

class TeamStandingPayLoad {
    val league = CommonLeague()
    val season = CommonSeason()
    val team = TeamStand()
}

class TeamStand {
    val profile = CommonProfile()
    val standings = Standings()
    val rank = Rank()
    val coach = Coach()
}

class Standings {
    val clinched = ""
    val confRank = 0
    val divRank = 0
    val last10 = ""
    val losses = 0
    val onHotStreak = ""
    val streak = ""
    val wins = 0
}

class Rank {
    val apg = ""
    val oppg = ""
    val ppg = ""
    val rpg = ""
}

class Coach {
    val headCoach = ""
}