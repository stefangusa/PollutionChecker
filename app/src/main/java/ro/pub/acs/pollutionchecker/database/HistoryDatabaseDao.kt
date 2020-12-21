package ro.pub.acs.pollutionchecker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HistoryDatabaseDao {

    @Insert
    suspend fun insert(searchedCity: SearchedCity)

    @Query("DELETE FROM searched_cities")
    suspend fun clear()

    @Query("DELETE FROM searched_cities WHERE searchId = :searchId")
    suspend fun remove(searchId: Long)

    @Query("SELECT * FROM searched_cities WHERE searchId = :searchId")
    fun get(searchId: Long): LiveData<SearchedCity>

    @Query("SELECT * FROM searched_cities ORDER BY timestamp DESC")
    fun getAll(): LiveData<List<SearchedCity>>
}