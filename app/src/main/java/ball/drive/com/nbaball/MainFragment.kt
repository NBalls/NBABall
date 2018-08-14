package ball.drive.com.nbaball

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import ball.drive.com.nbaball.event.OfficialItemClickEvent
import ball.drive.com.nbaball.event.TeamItemClickEvent
import ball.drive.com.nbaball.official.OfficialFragment
import ball.drive.com.nbaball.official.createOfficialFragment
import ball.drive.com.nbaball.official.createZtsFragment
import com.hwangjr.rxbus.annotation.Subscribe
import org.jetbrains.anko.onClick

/**
 * Created by aaron on 2018/4/11.
 * 主页面Fragment包含:计算页面，文案页面，拉取页面，Email页面
 */
class MainFragment: BaseFragment() {

    /**
     * 团队页面
     * 文案页面
     * 计算页面
     * 胜率页面
     * Email页面
     */
    private lateinit var scheduleFragment: ScheduleFragment
    private lateinit var parserFragment: ParserFragment
    private lateinit var teamFragment: TeamFragment
    private lateinit var officialFragment: OfficialFragment
    private lateinit var grabFragment: GrabFragment
    private lateinit var emailFragment: EmailFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamFragment = createTeamFragment()
        officialFragment = createOfficialFragment()
        scheduleFragment = createScheduleFragment()
        grabFragment = createGrabFragment()
        emailFragment = createEmailFragment()
        parserFragment = createParserFragment()
        loadRootFragment(R.id.mainContainerLayout, /*0,*/
                parserFragment/*, scheduleFragment, grabFragment, officialFragment, teamFragment, emailFragment*/)

        view?.findViewById<LinearLayout>(R.id.parserLayout)?.onClick {
            showHideFragment(parserFragment)
        }

        view?.findViewById<LinearLayout>(R.id.officialLayout)?.onClick {
            showHideFragment(officialFragment)
        }

        view?.findViewById<LinearLayout>(R.id.teamLayout)?.onClick {
            showHideFragment(teamFragment)
        }

        view?.findViewById<LinearLayout>(R.id.scheduleLayout)?.onClick {
            showHideFragment(scheduleFragment)
        }

        view?.findViewById<LinearLayout>(R.id.grabLayout)?.onClick {
            showHideFragment(grabFragment)
        }

        view?.findViewById<LinearLayout>(R.id.emailLayout)?.onClick {
            showHideFragment(emailFragment)
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