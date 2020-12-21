package ro.pub.acs.pollutionchecker.utils

interface Constants {
    companion object {

        const val CITY_NAME = "cityName"
        const val LAT = "valX"
        const val LON = "valY"

        const val EMPTY_ERROR = "Please enter a city name"
        const val API_ERROR = "Could not retrieve Air Quality data for $CITY_NAME."

        const val REQ_CODE = 3831

        private const val AQI_API_TOKEN = "0c8bd6186353ccad2ed8070faf95614b422a000e"
        const val AQI_API_URL = "https://api.waqi.info/feed/$CITY_NAME/?token=$AQI_API_TOKEN"

        const val GEO_API_URL = "https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=$LAT&longitude=$LON&localityLanguage=en"
    }

}