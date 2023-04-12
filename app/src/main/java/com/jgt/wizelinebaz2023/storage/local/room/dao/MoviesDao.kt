package com.jgt.wizelinebaz2023.storage.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.jgt.wizelinebaz2023.storage.local.room.entities.relations.MovieWithDetails
import kotlinx.coroutines.flow.Flow

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/

@Dao
interface MoviesDao {
    @Transaction
    @Query("SELECT * FROM movies WHERE movies.id = :id")
    fun getMovieDetails( id: Int ): Flow<MovieWithDetails>
}