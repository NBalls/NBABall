package ball.drive.com.nbaball

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import ball.drive.com.nbaball.network.APIClient
import com.google.gson.Gson
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val apiClient = APIClient()

    private val btTeamGroup by lazy {
        this.findViewById<Button>(R.id.btTeamGroup)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btTeamGroup.setOnClickListener {
            apiClient.requestTeamGroup()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ teamGroup ->
                        Log.i(TAG, Gson().toJson(teamGroup))
                    }, { e ->
                        e.printStackTrace()
                    })
        }
    }
}
