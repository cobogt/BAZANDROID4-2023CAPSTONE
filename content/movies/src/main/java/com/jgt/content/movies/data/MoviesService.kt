package com.jgt.content.movies.data

import com.jgt.content.movies.data.dto.CategoryMovieListResponse
import com.jgt.content.movies.data.dto.MovieDetailResponse
import com.jgt.content.movies.data.dto.MovieImagesResponse
import com.jgt.content.movies.data.dto.MovieKeywordsResponse
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
    suspend fun doGetMovieKeywordsRequest(
        @Path("movie_id") movieId: Int
    ): MovieKeywordsResponse

    @GET("/3/movie/{category}")
    suspend fun doGetMoviesByCategoryRequest(
        @Path("category") category: String
    ): CategoryMovieListResponse
}
