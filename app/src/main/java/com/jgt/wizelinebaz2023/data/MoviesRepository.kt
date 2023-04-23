package com.jgt.wizelinebaz2023.data

import com.jgt.wizelinebaz2023.data.dto.CategoryMovieListResponse
import com.jgt.wizelinebaz2023.data.dto.MovieDetailResponse
import com.jgt.wizelinebaz2023.domain.models.MovieDetail
import com.jgt.wizelinebaz2023.domain.models.MovieList
import com.jgt.wizelinebaz2023.storage.RepositoryStrategy
import com.jgt.wizelinebaz2023.storage.local.room.MoviesDatabase
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.CategoriesTable
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.KeywordsTable
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.MoviesTable
import com.jgt.wizelinebaz2023.storage.local.room.entities.crossref.MoviesCategoriesCrossRef
import com.jgt.wizelinebaz2023.storage.local.room.entities.relations.MovieWithDetails
import com.jgt.wizelinebaz2023.storage.remote.ApiClient
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/

class MoviesRepository @Inject constructor( moviesDatabase: MoviesDatabase ){
    private val moviesApiClient = ApiClient.createService( MoviesService::class.java )

    private val moviesDao       = moviesDatabase.moviesDao()
    private val imagesDao       = moviesDatabase.imagesDao()
    private val keywordsDao     = moviesDatabase.keywordsDao()
    private val categoriesDao   = moviesDatabase.categoriesDao()

    fun getMovieListByCategory( category: String ) =
        RepositoryStrategy.FetchTransformAndStoreStrategy<
                CategoryMovieListResponse, List<MoviesTable>, MovieList
        >(
            remoteSourceData = { moviesApiClient.doGetMoviesByCategoryRequest(
                category = listOf("upcoming", "top_rated", "popular")
                    .firstOrNull { it == category } ?: "upcoming" )
            },
            remoteToModelTransform = { it.toModel() },
            localSourceData = {
                categoriesDao.getMoviesFromCategory( category ).distinctUntilChanged()
            },
            localToModelTransform = { moviesTable: List<MoviesTable> ->
                 MovieList( movies = moviesTable.map { it.toModel() } )
            },
            storeModelTransformation = { model: MovieList ->
                val categoryId = listOf("upcoming", "top_rated", "popular").indexOf( category ) + 1
                categoriesDao.insert( CategoriesTable( id = categoryId, name = category ))

                model.movies.forEach { movie ->
                    moviesDao.saveMovie( movie.toEntity() )
                    moviesDao.saveMovieCategoryRelation(
                        MoviesCategoriesCrossRef( movie.id, categoryId ))
                }
            }
        )

    fun getMovieDetailById( movieId: Int ) =
        RepositoryStrategy.FetchTransformAndStoreStrategy<
                MovieDetailResponse, MovieWithDetails, MovieDetail>(
            remoteSourceData = { moviesApiClient.doGetMovieDetailsRequest( movieId ) },
            remoteToModelTransform = { it.toModel() },
            storeModelTransformation = { movieModel: MovieDetail ->
                moviesDao.saveMovie( movieModel.toEntity() )

                getMovieImagesById( movieId ).fetch()
                getMovieKeywordsById( movieId ).fetch()
            },
            localSourceData = { moviesDao.getMovieDetails( movieId ) },
            localToModelTransform = { it.toModel() }
        )

    private fun getMovieImagesById( movieId: Int ) =
        RepositoryStrategy.FetchAndTransformStrategy(
            remoteSourceData = { moviesApiClient.doGetMovieImagesRequest( movieId ) },
            remoteToModelTransform = { it.toModel() },
            storeModelTransformation = { imagesDao.insertAll( it.toEntity( movieId ) ) }
        )

    private fun getMovieKeywordsById( movieId: Int ) =
        RepositoryStrategy.FetchAndTransformStrategy(
            remoteSourceData = { moviesApiClient.doGetMovieKeywords( movieId ) },
            remoteToModelTransform = { it.toModel() },
            storeModelTransformation = { keywordsDao.insertKeywords(
                it.map { kw -> KeywordsTable( movieId = movieId, keyword = kw.word ) }
            ) }
        )
}
