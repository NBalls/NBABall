package ball.drive.com.nbaball

import android.os.Bundle
import android.util.Log
import android.widget.Button
import ball.drive.com.nbaball.network.APIClient
import com.google.gson.Gson
import me.yokeyword.fragmentation.SupportActivity
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : SupportActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val apiClient = APIClient()

    /*private val btTeamGroup by lazy {
        this.findViewById<Button>(R.id.btTeamGroup)
    }
    private val btTeamStats by lazy {
        this.findViewById<Button>(R.id.btTeamStats)
    }
    private val btTeamStanding by lazy {
        this.findViewById<Button>(R.id.btTeamStanding)
    }
    private val btTeamPlayer by lazy {
        this.findViewById<Button>(R.id.btTeamPlayer)
    }
    private val btTeamSchedule by lazy {
        this.findViewById<Button>(R.id.btTeamSchedule)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadRootFragment(R.id.mainLayout, createMainFragment())
        /*btTeamGroup.onClick {
            apiClient.requestTeamGroup()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ teamGroup ->
                        Log.i(TAG, Gson().toJson(teamGroup))
                    }, { e ->
                        e.printStackTrace()
                    })
        }

        btTeamStats.onClick {
            apiClient.requestTeamStats()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ teamStats ->
                        Log.i(TAG, Gson().toJson(teamStats))
                    }, { e ->
                        e.printStackTrace()
                    })
        }

        btTeamStanding.onClick {
            apiClient.requestTeamStanding()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ teamStanding ->
                        Log.i(TAG, Gson().toJson(teamStanding))
                    }, { e ->
                        e.printStackTrace()
                    })
        }

        btTeamPlayer.onClick {
            apiClient.requestTeamLeader()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ teamLeader ->
                        Log.i(TAG, Gson().toJson(teamLeader))
                    }, { e ->
                        e.printStackTrace()
                    })
        }

        btTeamSchedule.onClick {
            apiClient.requestGameSchedule()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ teamSchedule ->
                        Log.i(TAG, Gson().toJson(teamSchedule))
                    }, { e ->
                        e.printStackTrace()
                    })
        }*/
    }
}
