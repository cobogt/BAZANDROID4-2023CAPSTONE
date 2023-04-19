package com.jgt.wizelinebaz2023.data

import com.jgt.wizelinebaz2023.data.dto.CategoryMovieListResponse
import com.jgt.wizelinebaz2023.data.dto.MovieDetailResponse
import com.jgt.wizelinebaz2023.data.dto.MovieImagesResponse
import com.jgt.wizelinebaz2023.data.dto.MovieKeywordsResponse
import retrofit2.http.GET
import retrofit2.http.Path

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
interface MoviesService {
    @GET("/3/movie/{movie_id}")
    suspend fun doGetMovieDetailsRequest(
        @Path("movie_id") movieId: Int
    ): MovieDetailResponse

    @GET("/3/movie/{movie_id}/images")
    suspend fun doGetMovieImagesRequest(
        @Path("movie_id") movieId: Int
    ): MovieImagesResponse

    @GET("/3/movie/{movie_id}/keywords")
    suspend fun doGetMovieKeywords(
        @Path("movie_id") movieId: Int
    ): MovieKeywordsResponse

    @GET("/3/movie/latest")
    suspend fun doGetLatestMoviesRequest(): CategoryMovieListResponse

    @GET("/3/movie/popular")
    suspend fun doGetPopularMoviesRequest(): CategoryMovieListResponse

    @GET("/3/movie/upcoming")
    suspend fun doGetUpcomingMoviesRequest(): CategoryMovieListResponse

    @GET("/3/movie/top_rated")
    suspend fun doGetTopRatedMoviesRequest(): CategoryMovieListResponse

    @GET("/3/movie/now_playing")
    suspend fun doGetNowPlayingMoviesRequest(): CategoryMovieListResponse
}