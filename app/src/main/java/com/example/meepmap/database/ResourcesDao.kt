package com.example.meepmap.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.meepmap.model.Resource

@Dao
interface ResourcesDao {
    @Query("SELECT * FROM resource")
    fun getAll(): LiveData<List<Resource>?>

    @Query("SELECT * FROM resource WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): LiveData<List<Resource>?>

    @Query("SELECT * FROM resource WHERE companyZoneId = :companyZoneId")
    fun findByCompanyZoneId(companyZoneId: Int): LiveData<List<Resource>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(resources: List<Resource>)

    @Delete
    fun delete(resource: Resource)
}