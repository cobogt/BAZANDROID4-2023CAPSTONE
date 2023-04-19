package com.jgt.wizelinebaz2023.storage.local.room.dao

import androidx.room.*
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.CategoriesTable
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.MoviesTable
import kotlinx.coroutines.flow.Flow

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoriesTable>>

    @RewriteQueriesToDropUnusedColumns
    @Query(
        "SELECT * " +
        "FROM movies " +
        "LEFT JOIN movies_categories ON movies_categories.movieId = movies.id " +
        "LEFT JOIN categories ON movies_categories.categoryId = categories.id " +
        "WHERE categories.name = :categoryName")
    fun getMoviesFromCategory( categoryName: String ): Flow<List<MoviesTable>>

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    suspend fun updateCategories( categories: List<CategoriesTable> )
}