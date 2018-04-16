package ball.drive.com.nbaball

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ball.drive.com.nbaball.event.TeamItemClickEvent
import com.hwangjr.rxbus.annotation.Subscribe

/**
 * Created by aaron on 2018/4/11.
 */
class MainFragment: BaseFragment() {

    private lateinit var teamFragment: TeamFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamFragment = createTeamFragment()
        loadRootFragment(R.id.mainContainerLayout, teamFragment)
    }

    @Subscribe
    fun onTeamItemClickEvent(event: TeamItemClickEvent) {
        start(createTeamStatsFragment(event.teamId))
    }
}

fun createMainFragment(): MainFragment {
    return MainFragment()
}