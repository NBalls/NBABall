package ball.drive.com.nbaball

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation.SupportFragment

/**
 * Created by aaron on 2018/4/11.
 */
class MainFragment: SupportFragment() {

    private lateinit var teamFragment: TeamFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamFragment = createTeamFragment()
        loadRootFragment(R.id.mainContainerLayout, teamFragment)
    }
}

fun createMainFragment(): MainFragment {
    return MainFragment()
}