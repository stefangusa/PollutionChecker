package ro.pub.acs.pollutionchecker.city_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.pub.acs.pollutionchecker.database.HistoryDatabaseDao
import ro.pub.acs.pollutionchecker.database.SearchedCity

class CityInfoViewModel(
    dataSource: HistoryDatabaseDao
) : ViewModel() {

    private val database = dataSource
    var searchedCity: LiveData<SearchedCity>? = null

    private val _navigateToHistory = MutableLiveData<Boolean?>()

    val navigateToHistory: LiveData<Boolean?>
        get() = _navigateToHistory

    fun doneNavigating() {
        _navigateToHistory.value = false
    }

    fun retrieveCityInfo(searchId: Long) {
        searchedCity = database.get(searchId)
    }

    fun resetCityInfo() {
        searchedCity = null
    }

    private suspend fun insert(searchedCity: SearchedCity) {
        database.insert(searchedCity)
    }

    fun insertCity(searchedCity: SearchedCity) {
        viewModelScope.launch {
            insert(searchedCity)
        }
    }
}