package com.example.networkinteractions.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.networkinteractions.data.database.model.ThemeDb
import io.reactivex.rxjava3.core.Observable

@Dao
interface NewsThemeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = ThemeDb::class)
    fun saveTheme(themeDb: ThemeDb)


    @Query("delete from theme where id=:themeId")
    fun delete(themeId: Int)

}