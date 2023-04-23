package com.jgt.wizelinebaz2023.data.dto

import com.google.gson.annotations.SerializedName
import com.jgt.wizelinebaz2023.domain.models.MovieDetail
import com.jgt.wizelinebaz2023.domain.models.MovieList

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
data class CategoryMovieListResponse(
    val page:    Int = 0,
    val results: List<MovieListItem> = listOf(),
    val dates:   Dates? = null,
    @SerializedName("total_pages")   val totalPages:   Int = 0,
    @SerializedName("total_results") val totalResults: Int = 0,
) {
    data class Dates(
        val maximum: String,
        val minimum: String,
    )

    data class MovieListItem(
        val adult:             Boolean,
        val overview:          String,
        val id:                Int,
        val title:             String,
        val popularity:        Float,
        val video:             Boolean,
        @SerializedName("poster_path")       val posterPath:       String? = null,
        @SerializedName("release_date")      val releaseDate:      String,
        @SerializedName("genre_ids")         val genreIds:         List<Int> = listOf(),
        @SerializedName("original_title")    val originalTitle:    String,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("backdrop_path")     val backdropPath:     String? = null,
        @SerializedName("vote_count")        val voteCount:        Int,
        @SerializedName("vote_average")      val voteAverage:      Float,
    )

    fun toModel() = MovieList(
        page = page,
        movies = results.map { mli ->
            MovieDetail(
                mli.id,
                mli.title,
                "${mli.posterPath}" )
        })
}
