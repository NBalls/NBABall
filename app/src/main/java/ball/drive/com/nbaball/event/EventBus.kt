package ball.drive.com.nbaball.event

import com.hwangjr.rxbus.RxBus

/**
 * Created by aaron on 2018/4/13.
 */
fun postEvent(event: Any) {
    RxBus.get().post(event)
}

data class TeamItemClickEvent(val teamId: String)

data class OfficialItemClickEvent(val sender: String)