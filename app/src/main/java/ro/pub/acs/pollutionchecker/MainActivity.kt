package ro.pub.acs.pollutionchecker;

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ro.pub.acs.pollutionchecker.utils.Constants

class MainActivity : AppCompatActivity() {

    private var cityEditText: EditText? = null

    private val buttonClickListener = ButtonClickListener()

    private inner class ButtonClickListener : View.OnClickListener {

        override fun onClick(view: View) {
            when (view.id) {
                R.id.searchButton -> {
                    val cityName = cityEditText!!.text
                    if (!cityName.isNullOrBlank() && cityName.all {it.isLetter()}) {
                        val intent = Intent(applicationContext, SecondaryActivity::class.java)
                        intent.putExtra(Constants.CITY_NAME, cityName.toString())
                        startActivityForResult(intent, Constants.REQ_CODE)
                    } else {
                        Toast.makeText(applicationContext, Constants.EMPTY_ERROR, Toast.LENGTH_LONG).show()
                    }
                }
                R.id.historyButton -> {
                    val intent = Intent(applicationContext, SecondaryActivity::class.java)
                    startActivityForResult(intent, Constants.REQ_CODE)
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        cityEditText = findViewById(R.id.cityEditText)
        findViewById<Button>(R.id.searchButton).setOnClickListener(buttonClickListener)
        findViewById<Button>(R.id.historyButton).setOnClickListener(buttonClickListener)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == Constants.REQ_CODE) {
            cityEditText!!.text.clear()
        }

    }
}
