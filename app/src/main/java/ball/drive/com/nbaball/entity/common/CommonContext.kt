package ball.drive.com.nbaball.entity.common

/**
 * Created by aaron on 2018/4/2.
 */
class CommonContext {
    val user = CommonUser()
    val device = CommonDevice()
}

class CommonUser {
    val countryCode: String = ""
    val countryName: String = ""
    val locale: String = ""
    val timeZone: String = ""
    val timeZoneCity: String = ""
}

class CommonDevice {
    val clazz: String = ""
}

class CommonError {
    val detail: String = ""
    val isError: String = ""
    val message: String = ""
}

class CommonLeague {
    val id: String = ""
    val name: String = ""
}

class CommonSeason {
    val isCurrent: String = ""
    val rosterSeasonType: Int = 0
    val rosterSeasonYear: String = ""
    val rosterSeasonYearDisplay: String = ""
    val scheduleSeasonType: Int = 0
    val scheduleSeasonYear: String = ""
    val scheduleYearDisplay: String = ""
    val statsSeasonType: Int = 0
    val statsSeasonYear: String = ""
    val statsSeasonYearDisplay: String = ""
    val year: String = ""
    val yearDisplay: String = ""
}