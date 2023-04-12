package com.jgt.wizelinebaz2023.storage.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jgt.wizelinebaz2023.core.BaseApplication
import com.jgt.wizelinebaz2023.storage.local.room.dao.CategoriesDao
import com.jgt.wizelinebaz2023.storage.local.room.dao.ImagesDao
import com.jgt.wizelinebaz2023.storage.local.room.dao.KeywordsDao
import com.jgt.wizelinebaz2023.storage.local.room.dao.MoviesDao
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.CategoriesTable
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.ImagesTable
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.KeywordsTable
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.MoviesTable
import com.jgt.wizelinebaz2023.storage.local.room.entities.crossref.MoviesCategoriesCrossRef
import com.jgt.wizelinebaz2023.storage.local.room.entities.crossref.MoviesImagesCrossRef
import com.jgt.wizelinebaz2023.storage.local.room.entities.crossref.MoviesKeywordsCrossRef

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/

@Database(
    entities = [
        // Entidades base
        CategoriesTable::class,
        ImagesTable::class,
        KeywordsTable::class,
        MoviesTable::class,

        // Entidades de tablas pivote
        MoviesCategoriesCrossRef::class,
        MoviesImagesCrossRef::class,
        MoviesKeywordsCrossRef::class,

        // Entidades con relaciones finales
        // MovieWithDetails::class
   ],
    version = 1
)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun categoriesDao(): CategoriesDao
    abstract fun imagesDao(): ImagesDao
    abstract fun keywordsDao(): KeywordsDao

    companion object {
        private var moviesDatabase: MoviesDatabase? = null

        fun getDatabase(): MoviesDatabase {
            moviesDatabase = moviesDatabase ?: synchronized( this ) {
                Room.databaseBuilder(
                    context = BaseApplication.AppContext,
                    MoviesDatabase::class.java,
                    name = "movies_db"
                ).build()
            }

            return moviesDatabase !!
        }
    }
}