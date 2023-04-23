package com.jgt.wizelinebaz2023.storage.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.ImagesTable

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
@Dao
interface ImagesDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertAll( images: List<ImagesTable> )

    @Query("SELECT * FROM images WHERE movieId = :movieId")
    suspend fun getAll( movieId: Int ): List<ImagesTable>

    @Delete
    suspend fun deleteAll( images: List<ImagesTable> )
}
