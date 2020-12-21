package ro.pub.acs.pollutionchecker.city_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ro.pub.acs.pollutionchecker.database.HistoryDatabaseDao

class CityInfoViewModelFactory(
    private val dataSource: HistoryDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityInfoViewModel::class.java)) {
            return CityInfoViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}