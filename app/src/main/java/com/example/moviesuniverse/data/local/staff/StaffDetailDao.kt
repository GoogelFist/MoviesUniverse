package com.example.moviesuniverse.data.local.staff

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesuniverse.data.local.staff.model.StaffDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StaffDetailDao {

    @Query("SELECT * FROM staff_detail WHERE id LIKE :staffId")
    fun getStaffDetail(staffId: String): Flow<StaffDetailEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStaffDetail(staffDetail: StaffDetailEntity)

    @Query("DELETE FROM staff_detail WHERE id LIKE :staffId")
    suspend fun deleteStaffDetail(staffId: String)
}