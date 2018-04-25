package ball.drive.com.nbaball

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.*


/**
 * Created by aaron on 2018/4/11.
 */
class EmailFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_email, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.find<Button>(R.id.emailButton)?.onClick {
            val position = view?.findViewById<Spinner>(R.id.spPosition)?.selectedItemPosition
            var position1 = 1
            var position2 = position
            if (position.toString().length >= 2) {
                position1 = position!!.div(10)
                position2 = position % 10
            }
            getFromAssets("qqemail$position1.txt", position2!!, context)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ mList ->
                        val intent = Intent(Intent.ACTION_SEND)
                        for (i in 0..49) {
                            tos[i] = mList[i]
                        }
                        intent.putExtra(Intent.EXTRA_EMAIL, tos)
                        intent.putExtra(Intent.EXTRA_TEXT, "这是内容")
                        intent.putExtra(Intent.EXTRA_SUBJECT, "这是标题")
                        intent.type = "*/*"
                        intent.type = "message/rfc822"
                        Intent.createChooser(intent, "Choose Email Client")
                        startActivity(intent)
                    }, {})
        }
    }

    //从assets 文件夹中获取文件并读取数据
    private fun getFromAssets(fileName: String, position: Int, context: Context): Observable<List<String>> {
        return Observable.create<List<String>> { subscriber ->
            try {
                val `in` = context.resources.assets.open(fileName)
                val isr = InputStreamReader(`in`)
                val br = BufferedReader(isr)
                var line = br.readLine()
                val mList = ArrayList<String>()
                var count = 0
                while (line != null) {
                    if (count / 100 == position) {
                        mList.add(line + "@qq.com")
                    }
                    count ++
                    line = br.readLine()
                }
                subscriber.onNext(mList)
                subscriber.onCompleted()
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }
    }
}

fun createEmailFragment(): EmailFragment {
    return EmailFragment()
}

val tos = arrayOf("", "", "", "", "", "", "", "", "", "",
        "", "", "", "", "", "", "", "", "", "",
        "", "", "", "", "", "", "", "", "", "",
        "", "", "", "", "", "", "", "", "", "",
        "", "", "", "", "", "", "", "", "", "")