package com.jgt.wizelinebaz2023.data

import android.util.Log
import com.jgt.wizelinebaz2023.data.dto.CategoryMovieListResponse
import com.jgt.wizelinebaz2023.domain.models.MovieDetail
import com.jgt.wizelinebaz2023.domain.models.MovieList
import com.jgt.wizelinebaz2023.storage.RepositoryStrategy
import com.jgt.wizelinebaz2023.storage.local.room.MoviesDatabase
import com.jgt.wizelinebaz2023.storage.remote.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class MoviesRepository {
    private val moviesApiClient = ApiClient.createService( MoviesService::class.java )
    private val moviesDao       = MoviesDatabase.getDatabase().moviesDao()

    private val getMovieListByCategoryStateFlow = MutableStateFlow( CategoryMovieListResponse() )
    fun getMovieListByCategory( category: String ) =
        RepositoryStrategy.FetchTransformAndStoreStrategy<CategoryMovieListResponse, CategoryMovieListResponse, MovieList>(
        remoteSourceData = {
            when( category ) {
                "upcoming"   -> moviesApiClient.doGetUpcomingMoviesRequest()
                "top_rated"  -> moviesApiClient.doGetTopRatedMoviesRequest()
                "popular"    -> moviesApiClient.doGetPopularMoviesRequest()
                else         -> moviesApiClient.doGetUpcomingMoviesRequest()
            }
        },
        remoteToModelTransform = {
            Log.w("MoviesRepository", it.toString())
            MovieList(
                it.page,
                it.results.map { mli ->
                    MovieDetail( mli.title, "${mli.posterPath}" )
                })
        },
        storeRemoteResult = {
            getMovieListByCategoryStateFlow.value = it
        },
        localToModelTransform = {
            MovieList(
                it.page,
                it.results.map { mli ->
                    MovieDetail( mli.title, "${mli.posterPath}" )
                })
        },
        localSourceData = { getMovieListByCategoryStateFlow }
    )
}