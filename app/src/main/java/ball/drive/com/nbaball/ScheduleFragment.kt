package ball.drive.com.nbaball

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.onClick
import java.text.DecimalFormat

/**
 * Created by aaron on 2018/4/11.
 */
class ScheduleFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.findViewById<Button>(R.id.btSubmit)?.onClick {
            try {
                val etNormalRate = view.findViewById<EditText>(R.id.etNormalRate).text.toString().toFloat()
                val etR1 = view.findViewById<EditText>(R.id.etR1).text.toString().toFloat()
                val etR2 = view.findViewById<EditText>(R.id.etR2).text.toString().toFloat()
                val etR3 = view.findViewById<EditText>(R.id.etR3).text.toString().toFloat()

                val et1 = etNormalRate / etR1
                val et2 = etNormalRate / etR2
                val et3 = etNormalRate / etR3

                view.findViewById<TextView>(R.id.etRate1).text = DecimalFormat("0.000").format(et1)
                view.findViewById<TextView>(R.id.etRate2).text = DecimalFormat("0.000").format(et2)
                view.findViewById<TextView>(R.id.etRate3).text = DecimalFormat("0.000").format(et3)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "程序出现异常，请检查输入数据!!!", Toast.LENGTH_SHORT).show()
            }
        }

        view?.findViewById<Button>(R.id.btReset)?.onClick {
            try {
                view.findViewById<EditText>(R.id.etR1).setText("")
                view.findViewById<EditText>(R.id.etR2).setText("")
                view.findViewById<EditText>(R.id.etR3).setText("")
                view?.findViewById<TextView>(R.id.etRate1)?.text = ""
                view?.findViewById<TextView>(R.id.etRate2)?.text = ""
                view?.findViewById<TextView>(R.id.etRate3)?.text = ""
                resetEdit()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "程序出现异常，请检查输入数据!!!", Toast.LENGTH_SHORT).show()
            }
        }

        view?.findViewById<Button>(R.id.btResetTs)?.onClick {
            try {
                view.findViewById<EditText>(R.id.etX).setText("")
                view.findViewById<EditText>(R.id.etY).setText("")
                resetEdit()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "程序出现异常，请检查输入数据!!!", Toast.LENGTH_SHORT).show()
            }
        }

        view?.findViewById<Button>(R.id.btE1)?.onClick {
            try {
                val r1 = view.findViewById<TextView>(R.id.etRate1).text.toString().toFloat()
                val r2 = view.findViewById<TextView>(R.id.etRate2).text.toString().toFloat()
                val r3 = view.findViewById<TextView>(R.id.etRate3).text.toString().toFloat()
                val x = view.findViewById<EditText>(R.id.etX).text.toString().toFloat()
                val y = view.findViewById<EditText>(R.id.etY).text.toString().toFloat()

                view.findViewById<TextView>(R.id.etEl1)?.text = DecimalFormat("0.000").format(r1 * x)
                view.findViewById<TextView>(R.id.etEr1)?.text = DecimalFormat("0.000").format(r2* 0.5 * y + r3 * y)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "程序出现异常，请检查输入数据!!!", Toast.LENGTH_SHORT).show()
            }
        }

        view?.findViewById<Button>(R.id.btE2)?.onClick {
            try {
                val r1 = view.findViewById<TextView>(R.id.etRate1).text.toString().toFloat()
                val r2 = view.findViewById<TextView>(R.id.etRate2).text.toString().toFloat()
                val r3 = view.findViewById<TextView>(R.id.etRate3).text.toString().toFloat()
                val x = view.findViewById<EditText>(R.id.etX).text.toString().toFloat()
                val y = view.findViewById<EditText>(R.id.etY).text.toString().toFloat()

                view.findViewById<TextView>(R.id.etEl2)?.text = DecimalFormat("0.000").format(r1 * x)
                view.findViewById<TextView>(R.id.etEr2)?.text = DecimalFormat("0.000").format(r3 * y)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "程序出现异常，请检查输入数据!!!", Toast.LENGTH_SHORT).show()
            }
        }

        view?.findViewById<Button>(R.id.btE3)?.onClick {
            try {
                val r1 = view.findViewById<TextView>(R.id.etRate1).text.toString().toFloat()
                val r2 = view.findViewById<TextView>(R.id.etRate2).text.toString().toFloat()
                val r3 = view.findViewById<TextView>(R.id.etRate3).text.toString().toFloat()
                val x = view.findViewById<EditText>(R.id.etX).text.toString().toFloat()
                val y = view.findViewById<EditText>(R.id.etY).text.toString().toFloat()

                view.findViewById<TextView>(R.id.etEl3)?.text = DecimalFormat("0.000").format(r1 * x)
                view.findViewById<TextView>(R.id.etEr3)?.text = DecimalFormat("0.000").format(r2 * y + r3 * y)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "程序出现异常，请检查输入数据!!!", Toast.LENGTH_SHORT).show()
            }
        }

        view?.findViewById<Button>(R.id.btE4)?.onClick {
            try {
                val r1 = view.findViewById<TextView>(R.id.etRate1).text.toString().toFloat()
                val r2 = view.findViewById<TextView>(R.id.etRate2).text.toString().toFloat()
                val r3 = view.findViewById<TextView>(R.id.etRate3).text.toString().toFloat()
                val x = view.findViewById<EditText>(R.id.etX).text.toString().toFloat()
                val y = view.findViewById<EditText>(R.id.etY).text.toString().toFloat()

                view.findViewById<TextView>(R.id.etEl4)?.text = DecimalFormat("0.000").format(r1 * x - (r2 / (r1 + r2)) * 0.5 * x)
                view.findViewById<TextView>(R.id.etEr4)?.text = DecimalFormat("0.000").format(r2 * y + r3 * y)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "程序出现异常，请检查输入数据!!!", Toast.LENGTH_SHORT).show()
            }
        }

        view?.findViewById<Button>(R.id.btE5)?.onClick {
            try {
                val r1 = view.findViewById<TextView>(R.id.etRate1).text.toString().toFloat()
                val r2 = view.findViewById<TextView>(R.id.etRate2).text.toString().toFloat()
                val r3 = view.findViewById<TextView>(R.id.etRate3).text.toString().toFloat()
                val x = view.findViewById<EditText>(R.id.etX).text.toString().toFloat()
                val y = view.findViewById<EditText>(R.id.etY).text.toString().toFloat()

                view.findViewById<TextView>(R.id.etEl5)?.text = DecimalFormat("0.000").format(r1 * x - (r2 / (r1 + r2)) * x)
                view.findViewById<TextView>(R.id.etEr5)?.text = DecimalFormat("0.000").format(r2 * y + r3 * y)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "程序出现异常，请检查输入数据!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resetEdit() {
        view?.findViewById<TextView>(R.id.etEl1)?.text = ""
        view?.findViewById<TextView>(R.id.etEr1)?.text = ""
        view?.findViewById<TextView>(R.id.etEl2)?.text = ""
        view?.findViewById<TextView>(R.id.etEr2)?.text = ""
        view?.findViewById<TextView>(R.id.etEl3)?.text = ""
        view?.findViewById<TextView>(R.id.etEr3)?.text = ""
        view?.findViewById<TextView>(R.id.etEl4)?.text = ""
        view?.findViewById<TextView>(R.id.etEr4)?.text = ""
        view?.findViewById<TextView>(R.id.etEl5)?.text = ""
        view?.findViewById<TextView>(R.id.etEr5)?.text = ""
    }
}

fun createScheduleFragment(): ScheduleFragment {
    return ScheduleFragment()
}