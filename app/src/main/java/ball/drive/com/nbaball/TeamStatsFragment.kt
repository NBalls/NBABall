package ball.drive.com.nbaball

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ball.drive.com.nbaball.network.APIClient

/**
 * Created by aaron on 2018/4/11.
 */
class TeamStatsFragment: BaseFragment() {

    private val apiClient by lazy {
        APIClient()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_stats, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

fun createTeamStatsFragment(): TeamStatsFragment {
    return TeamStatsFragment()
}