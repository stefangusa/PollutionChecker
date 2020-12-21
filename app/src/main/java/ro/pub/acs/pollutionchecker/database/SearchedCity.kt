package ro.pub.acs.pollutionchecker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ro.pub.acs.pollutionchecker.utils.Helper.Companion.toDateString

@Entity(tableName = "searched_cities")
data class SearchedCity(
    @PrimaryKey(autoGenerate = true)
    var searchId: Long = 0L,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "region")
    val region: String,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "aqi")
    var aqi: Float,

    @ColumnInfo(name = "pm25")
    var pm25: Float,

    @ColumnInfo(name = "pm10")
    var pm10: Float,

    @ColumnInfo(name = "o3")
    var o3: Float,

    @ColumnInfo(name = "no2")
    var no2: Float,

    @ColumnInfo(name = "so2")
    var so2: Float,

    @ColumnInfo(name = "co")
    var co: Float,

    @ColumnInfo(name = "timestamp")
    var timestamp: String = System.currentTimeMillis().toDateString()
)

