package ball.drive.com.nbaball

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import ball.drive.com.nbaball.event.OfficialItemClickEvent
import ball.drive.com.nbaball.event.TeamItemClickEvent
import ball.drive.com.nbaball.official.OfficialFragment
import ball.drive.com.nbaball.official.createOfficialFragment
import ball.drive.com.nbaball.official.createZtsFragment
import com.hwangjr.rxbus.annotation.Subscribe
import org.jetbrains.anko.onClick

/**
 * Created by aaron on 2018/4/11.
 */
class MainFragment: BaseFragment() {

    private lateinit var teamFragment: TeamFragment
    private lateinit var officialFragment: OfficialFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamFragment = createTeamFragment()
        officialFragment = createOfficialFragment()
        loadMultipleRootFragment(R.id.mainContainerLayout, 0,
                officialFragment, teamFragment)

        view?.findViewById<LinearLayout>(R.id.officialLayout)?.onClick {
            showHideFragment(officialFragment)
        }

        view?.findViewById<LinearLayout>(R.id.teamLayout)?.onClick {
            showHideFragment(teamFragment)
        }

        view?.findViewById<LinearLayout>(R.id.scheduleLayout)?.onClick {
            Toast.makeText(context, "正在开发中.....", Toast.LENGTH_LONG).show()
        }
    }

    @Subscribe
    fun onOfficialItemClickEvent(event: OfficialItemClickEvent) {
        start(createZtsFragment())
    }

    @Subscribe
    fun onTeamItemClickEvent(event: TeamItemClickEvent) {
        start(createTeamStatsFragment(event.teamId))
    }
}

fun createMainFragment(): MainFragment {
    return MainFragment()
}