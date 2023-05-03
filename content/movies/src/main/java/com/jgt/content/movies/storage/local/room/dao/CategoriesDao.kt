package com.jgt.content.movies.storage.local.room.dao

import androidx.room.Query
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.jgt.content.movies.storage.local.room.entities.base.CategoriesTable
import com.jgt.content.movies.storage.local.room.entities.base.MoviesTable

import kotlinx.coroutines.flow.Flow

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
@RewriteQueriesToDropUnusedColumns
@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoriesTable>>

    @Query(
        "SELECT movies.*, categories.id as category_id, categories.name as category_name " +
        "FROM categories " +
        "JOIN movies_categories ON movies_categories.movieId = movies.id " +
        "JOIN movies ON movies_categories.categoryId = categories.id " +
        "WHERE categories.name LIKE :categoryName")
    fun getMoviesFromCategory( categoryName: String ): Flow<List<MoviesTable>>

    @Insert( onConflict = OnConflictStrategy.IGNORE )
    suspend fun insert( category: CategoriesTable ): Long
}
