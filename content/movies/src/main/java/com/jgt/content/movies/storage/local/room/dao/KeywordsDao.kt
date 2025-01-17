package com.jgt.content.movies.storage.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.jgt.content.movies.storage.local.room.entities.base.KeywordsTable

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
@Dao
interface KeywordsDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    fun insertKeywords( keyword: List<KeywordsTable> )
}
