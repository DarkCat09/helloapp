package ru.dc09.helloapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private var pThread: Progress? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val birthdayEditText: EditText = findViewById(R.id.birthdayEditText)
        val imageView: ImageView = findViewById(R.id.imageView)
        val devCheckBox: CheckBox = findViewById(R.id.devCheckBox)

        val helloTextView: TextView = findViewById(R.id.helloTextView)
        val ageTextView: TextView = findViewById(R.id.ageTextView)
        progressBar = findViewById(R.id.progressBar)

        val pictures = listOf(
            getDrawable(R.drawable.pic1),
            getDrawable(R.drawable.pic2),
            getDrawable(R.drawable.pic3),
            getDrawable(R.drawable.pic4),
            getDrawable(R.drawable.pic5),
            getDrawable(R.drawable.pic6),
            getDrawable(R.drawable.pic7),
            getDrawable(R.drawable.pic8),
            getDrawable(R.drawable.pic9),
            getDrawable(R.drawable.pic10),
            getDrawable(R.drawable.pic11),
            getDrawable(R.drawable.pic12),
            getDrawable(R.drawable.pic13),
            getDrawable(R.drawable.pic14),
            getDrawable(R.drawable.pic15),
        )

        findViewById<Button>(R.id.button).setOnClickListener {

            pThread?.interrupt()

            pThread = Progress(progressBar)
            pThread!!.start()

            val name = nameEditText.text.toString()
            val birthday = birthdayEditText.text.toString()
            val cal = Calendar.getInstance()

            if (name.trim() == "") {
                Toast.makeText(
                    this,
                    getString(R.string.name_error),
                    Toast.LENGTH_SHORT
                ).show()
                pThread!!.interrupt()
                return@setOnClickListener
            }

            val date = Regex("(\\d+)[.-](\\d+)[.-](\\d+)")
            val res = date.find(birthday)
            if (res == null) {
                Toast.makeText(
                    this,
                    getString(R.string.date_error),
                    Toast.LENGTH_SHORT
                ).show()
                pThread!!.interrupt()
                return@setOnClickListener
            }
            val grp = res.groupValues
            val (day, month, year) =
                grp.subList(1, grp.size).map {
                    Integer.parseInt(it, 10)
                }

            val devBirthday = (year == 2009 && month == 7 && day == 13)
            devCheckBox.visibility = if (devBirthday) View.VISIBLE else View.GONE

            helloTextView.text = getString(R.string.hello_text, name)
            ageTextView.text = getString(
                R.string.age_text,
                cal.get(Calendar.YEAR) - year
            )

            imageView.setImageDrawable(
                if (devCheckBox.isChecked)
                    getDrawable(R.drawable.dev)
                else
                    pictures.random()
            )
        }
    }
}

class Progress(private val pb: ProgressBar) : Thread() {
    override fun run() {
        for (i in 0..101 step 2) {
            try {
                pb.progress = i
                sleep(15L)
            }
            catch (_: InterruptedException) {
                pb.progress = 0
                break
            }
        }
    }
}