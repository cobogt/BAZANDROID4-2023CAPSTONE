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
    @GET("/movie/{movie_id}")
    suspend fun doGetMovieDetailsRequest(
        @Path("movie_id") movieId: Int
    ): MovieDetailResponse

    @GET("/movie/{movie_id}/images")
    suspend fun doGetMovieImagesRequest(
        @Path("movie_id") movieId: Int
    ): MovieImagesResponse

    @GET("/movie/{movie_id}/keywords")
    suspend fun doGetMovieKeywords(
        @Path("movie_id") movieId: Int
    ): MovieKeywordsResponse

    @GET("/movie/latest")
    suspend fun doGetLatestMoviesRequest(): CategoryMovieListResponse

    @GET("/movie/popular")
    suspend fun doGetPopularMoviesRequest(): CategoryMovieListResponse

    @GET("/movie/upcoming")
    suspend fun doGetUpcomingMoviesRequest(): CategoryMovieListResponse

    @GET("/movie/top_rated")
    suspend fun doGetTopRatedMoviesRequest(): CategoryMovieListResponse

    @GET("/movie/now_playing")
    suspend fun doGetNowPlayingMoviesRequest(): CategoryMovieListResponse
}