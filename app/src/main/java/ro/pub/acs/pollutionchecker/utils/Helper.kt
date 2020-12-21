package ro.pub.acs.pollutionchecker.utils

import androidx.core.content.ContextCompat
import ro.pub.acs.pollutionchecker.R
import java.text.DateFormat
import java.util.Locale

class Helper {
    companion object {
        fun Long.toDateString(format: Int = DateFormat.MEDIUM): String {
            val df = DateFormat.getDateTimeInstance(format, format, Locale.getDefault())
            return df.format(this)
        }

        fun getRating(aqi: Float): Triple<Int, Int, Int> {
            when (aqi) {
                in 0f..50f -> {
                    return Triple(R.drawable.ic_face_good, R.color.aqius_good, R.string.aqi_good)
                }
                in 50.1f..100f -> {
                    return Triple(
                        R.drawable.ic_face_moderate,
                        R.color.aqius_moderate,
                        R.string.aqi_moderate
                    )
                }
                in 100.1f..150f -> {
                    return Triple(
                        R.drawable.ic_face_unhealthy_for_sensitive_groups,
                        R.color.aqius_unhealthyforsensitivegroups,
                        R.string.aqi_unhealthyforsensitivegroups
                    )
                }
                in 150.1f..200f -> {
                    return Triple(
                        R.drawable.ic_face_unhealthy,
                        R.color.aqius_unhealthy,
                        R.string.aqi_unhealthy
                    )
                }
                in 200.1f..300f -> {
                    return Triple(
                        R.drawable.ic_face_very_unhealthy,
                        R.color.aqius_veryunhealthy,
                        R.string.aqi_veryunhealthy
                    )
                }
                else -> {
                    return Triple(
                        R.drawable.ic_face_hazardous,
                        R.color.aqius_hazardous,
                        R.string.aqi_hazardous
                    )
                }
            }
        }
    }
}