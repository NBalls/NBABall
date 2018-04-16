package ball.drive.com.nbaball

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ball.drive.com.nbaball.entity.StatsAverage
import ball.drive.com.nbaball.entity.TeamStats
import ball.drive.com.nbaball.entity.TeamsStats
import ball.drive.com.nbaball.network.APIClient
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by aaron on 2018/4/11.
 */
class TeamStatsFragment: BaseFragment() {

    private lateinit var teamId: String

    private val apiClient by lazy {
        APIClient()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_stats, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teamId = arguments.getString("teamId")
        apiClient.requestTeamStats()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ teamStats ->
                    val teamsStats = parseData(teamStats)
                    updateUI(teamsStats, teamStats.payload.leagueSeasonAverage)
                    parsePosition(teamStats)
                }, { e ->
                    e.printStackTrace()
                })
    }

    private fun updateUI(teamsStats: TeamsStats?, league: StatsAverage) {
        if (teamsStats != null) {
            view?.findViewById<TextView>(R.id.teamName)?.text = teamsStats.profile.name
            view?.findViewById<TextView>(R.id.teamPoint)?.text = teamsStats.statAverage.pointsPg.toString()
            view?.findViewById<TextView>(R.id.leaguePoint)?.text = league.pointsPg.toString()
            view?.findViewById<TextView>(R.id.teamRebs)?.text = teamsStats.statAverage.rebsPg.toString()
            view?.findViewById<TextView>(R.id.leagueRebs)?.text = league.rebsPg.toString()
            view?.findViewById<TextView>(R.id.teamAssist)?.text = teamsStats.statAverage.assistsPg.toString()
            view?.findViewById<TextView>(R.id.leagueAssist)?.text = league.assistsPg.toString()
            view?.findViewById<TextView>(R.id.teamSteal)?.text = teamsStats.statAverage.stealsPg.toString()
            view?.findViewById<TextView>(R.id.leagueSteal)?.text = league.stealsPg.toString()
            view?.findViewById<TextView>(R.id.teamBlock)?.text = teamsStats.statAverage.blocksPg.toString()
            view?.findViewById<TextView>(R.id.leagueBlock)?.text = league.blocksPg.toString()
            view?.findViewById<TextView>(R.id.teamFgp)?.text = teamsStats.statAverage.fgpct.toString()
            view?.findViewById<TextView>(R.id.leagueFgp)?.text = league.fgpct.toString()
            view?.findViewById<TextView>(R.id.teamFta)?.text = teamsStats.statAverage.ftaPg.toString()
            view?.findViewById<TextView>(R.id.leagueFta)?.text = league.ftaPg.toString()
        }
    }

    private fun parseData(teamStats: TeamStats): TeamsStats? {
        var teamsStats:TeamsStats ?= null
        for (i in 0 until teamStats.payload.teams.size) {
            if (teamId == teamStats.payload.teams[i].profile.id) {
                teamsStats = teamStats.payload.teams[i]
            }
        }

        return teamsStats
    }

    private fun parsePosition(teamStats: TeamStats) {
        Collections.sort(teamStats.payload.teams) { o1, o2 -> o2.statAverage.pointsPg.compareTo(o1.statAverage.pointsPg) }
        view?.findViewById<TextView>(R.id.teamPointSort)?.text = "排名" + getPosition(teamId, teamStats)
        Collections.sort(teamStats.payload.teams) { o1, o2 -> o2.statAverage.pointsPg.compareTo(o1.statAverage.pointsPg) }
    }

    private fun getPosition(teamId: String, teamStats: TeamStats): Int {
        var position = 0
        for (i in 0 until teamStats.payload.teams.size) {
            if (teamId == teamStats.payload.teams[i].profile.id) {
                position = i
            }
        }
        return position
    }
}

fun createTeamStatsFragment(teamId: String): TeamStatsFragment {
    val teamStatsFragment = TeamStatsFragment()
    teamStatsFragment.arguments = Bundle()
    teamStatsFragment.arguments.putString("teamId", teamId)
    return teamStatsFragment
}