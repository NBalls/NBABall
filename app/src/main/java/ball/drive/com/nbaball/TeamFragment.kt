package ball.drive.com.nbaball

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import ball.drive.com.nbaball.entity.TeamGroup
import ball.drive.com.nbaball.entity.common.CommonProfile
import ball.drive.com.nbaball.network.APIClient
import me.yokeyword.fragmentation.SupportFragment
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by aaron on 2018/4/11.
 */
class TeamFragment: SupportFragment() {

    private val recyclerView by lazy {
        view!!.findViewById<RecyclerView>(R.id.recyclerView)
    }
    private val apiClient by lazy {
        APIClient()
    }
    private lateinit var teamAdapter: TeamAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teamAdapter = TeamAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = teamAdapter
        apiClient.requestTeamGroup()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ teamGroup ->
                    teamAdapter.addHeaderTeamGroup(teamGroup)
                }, { e ->
                    e.printStackTrace()
                })
    }
}

class TeamAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val data = ArrayList<CommonProfile>()

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_team_item, null, false)
        rootView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        return TeamViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as TeamViewHolder).setItem(data[position])
    }

    fun addHeaderTeamGroup(teamGroup: TeamGroup) {
        data.clear()
        data.addAll(parserData(teamGroup))
        notifyDataSetChanged()
    }

    private fun parserData(teamGroup: TeamGroup): List<CommonProfile> {
        val mList = ArrayList<CommonProfile>()
        for (i in 0 until teamGroup.payload.listGroups.size) {
            for (j in 0 until teamGroup.payload.listGroups[i].teams.size) {
                mList.add(teamGroup.payload.listGroups[i].teams[j].profile)
            }
        }

        Collections.sort(mList) { o1, o2 -> o1.division.compareTo(o2.division) }
        return mList
    }
}

class TeamViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun setItem(commonProfile: CommonProfile) {
        itemView.findViewById<TextView>(R.id.teamName).text = commonProfile.name
        itemView.findViewById<TextView>(R.id.teamCity).text = commonProfile.city
        itemView.findViewById<TextView>(R.id.teamDivision).text = commonProfile.division
        itemView.findViewById<TextView>(R.id.teamPosition).text = commonProfile.displayConference

        itemView.onClick {
            
        }
    }
}

fun createTeamFragment(): TeamFragment {
    return TeamFragment()
}