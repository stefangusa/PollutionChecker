package ro.pub.acs.pollutionchecker.history

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ro.pub.acs.pollutionchecker.database.HistoryDatabaseDao
import ro.pub.acs.pollutionchecker.database.SearchedCity

class HistoryViewModel (
    dataSource: HistoryDatabaseDao
) : ViewModel() {

    private val database = dataSource

    val history = database.getAll()

    private var _showSnackbarEvent = MutableLiveData<Boolean?>()

    val showSnackBarEvent: LiveData<Boolean?>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    private suspend fun clear() {
        database.clear()
    }

    private suspend fun remove(searchId: Long) {
        database.remove(searchId)
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
            _showSnackbarEvent.value = true
        }
    }

    fun removeItem(searchId: Long) {
        viewModelScope.launch {
            remove(searchId)
        }
    }
}