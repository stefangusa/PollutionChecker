package ro.pub.acs.pollutionchecker

import android.os.Bundle
import android.view.View.GONE
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import ro.pub.acs.pollutionchecker.utils.Constants


class SecondaryActivity : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val navController = (supportFragmentManager.findFragmentById(R.id.baseNavHostFragment) as NavHostFragment).navController
        if (!navController.popBackStack()) {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.baseNavHostFragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.navigation)

        val cityName: String? = intent.getStringExtra(Constants.CITY_NAME)

        fun onAPIError(cityName: String) {
            Toast.makeText(
                applicationContext,
                Constants.API_ERROR.replace(
                    Constants.CITY_NAME,
                    cityName),
                Toast.LENGTH_LONG
            ).show()
            finish()
        }

        if (cityName != null) {
            val queue = Volley.newRequestQueue(this)
            val getAqiRequest = StringRequest(
                Request.Method.GET,
                Constants.AQI_API_URL.replace(Constants.CITY_NAME, cityName),
                { response ->
                    val responseJson = JSONObject(response)
                    if (responseJson.getString("status").equals("ok", true)) {
                        val data = responseJson.getJSONObject(
                            "data"
                        )
                        val aqi = try {
                            data.getDouble("aqi").toFloat()
                        } catch (e: JSONException) {
                            -1F
                        }

                        val iaqi = data.getJSONObject("iaqi")
                        val pm25 = try {
                            iaqi.getJSONObject("pm25").getDouble("v").toFloat()
                        } catch (e: JSONException) {
                            -1F
                        }
                        val pm10 = try {
                            iaqi.getJSONObject("pm10").getDouble("v").toFloat()
                        } catch (e: JSONException) {
                            -1F
                        }
                        val o3 = try {
                            iaqi.getJSONObject("o3").getDouble("v").toFloat()
                        } catch (e: JSONException) {
                            -1F
                        }
                        val no2 = try {
                            iaqi.getJSONObject("no2").getDouble("v").toFloat()
                        } catch (e: JSONException) {
                            -1F
                        }
                        val so2 = try {
                            iaqi.getJSONObject("so2").getDouble("v").toFloat()
                        } catch (e: JSONException) {
                            -1F
                        }
                        val co = try {
                            iaqi.getJSONObject("co").getDouble("v").toFloat()
                        } catch (e: JSONException) {
                            -1F
                        }

                        val coord = data.getJSONObject("city").getJSONArray("geo")
                        val lat = coord.getDouble(0).toString()
                        val lon = coord.getDouble(1).toString()

                        val geoApiUrl = Constants.GEO_API_URL
                                .replace(Constants.LAT, lat)
                                .replace(Constants.LON, lon)

                        val getGeoRequest = StringRequest(
                            Request.Method.GET,
                            geoApiUrl,
                            { res ->
                                val responseGeo = JSONObject(res)
                                val region = responseGeo.getString("principalSubdivision")
                                val country = responseGeo.getString("countryName")
                                val city = responseGeo.getString("city")

                                graph.startDestination = R.id.cityInfoFragment
                                navHostFragment.navController.setGraph(
                                    graph, bundleOf(
                                        "city" to city, "region" to region, "country" to country,
                                        "aqi" to aqi, "pm25" to pm25, "pm10" to pm10,
                                        "o3" to o3, "no2" to no2, "so2" to so2, "co" to co
                                    )
                                )
                                findViewById<RelativeLayout>(R.id.loading).visibility = GONE
                            },
                            {
                                onAPIError(cityName)
                            }
                        )
                        queue.add(getGeoRequest)
                    } else {
                        onAPIError(cityName)
                    }
                },
                {
                    onAPIError(cityName)
                }
            )
            queue.add(getAqiRequest)
        } else {
            graph.startDestination = R.id.historyFragment
            navHostFragment.navController.graph = graph
            findViewById<RelativeLayout>(R.id.loading).visibility = GONE
        }
    }
}