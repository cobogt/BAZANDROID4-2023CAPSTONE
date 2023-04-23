package com.jgt.wizelinebaz2023.data

import com.jgt.wizelinebaz2023.storage.RepositoryStrategy
import com.jgt.wizelinebaz2023.storage.local.room.MoviesDatabase
import com.jgt.wizelinebaz2023.storage.remote.ApiClient
import javax.inject.Inject

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/

class MoviesRepository @Inject constructor( moviesDatabase: MoviesDatabase ){
    private val moviesApiClient = ApiClient.createService( MoviesService::class.java )
    private val moviesDao       = moviesDatabase.moviesDao()

    fun getMovieListByCategory( category: String ) =
        RepositoryStrategy.FetchAndTransformStrategy(
            remoteSourceData = { moviesApiClient.doGetMoviesByCategoryRequest(
                category = listOf("upcoming", "top_rated", "popular")
                    .firstOrNull { it == category } ?: "upcoming" )
            },
            remoteToModelTransform = { it.toModel() },
        )

    fun getMovieDetailById( movieId: Int ) =
        RepositoryStrategy.FetchAndTransformStrategy(
            remoteSourceData = { moviesApiClient.doGetMovieDetailsRequest( movieId ) },
            remoteToModelTransform = { it.toModel() }
        )
}
