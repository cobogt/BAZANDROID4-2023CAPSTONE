package com.jgt.wizelinebaz2023.data.dto

import com.google.gson.annotations.SerializedName

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
data class CategoryMovieListResponse(
    val page:    Int,
    val results: List<MovieListItem> = listOf(),
    val dates:   Dates,
    @SerializedName("total_pages")   val totalPages:   Int,
    @SerializedName("total_results") val totalResults: Int,
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
        val popularity:        Int,
        val video:             Boolean,
        @SerializedName("poster_path")       val posterPath:       String? = null,
        @SerializedName("release_date")      val releaseDate:      String,
        @SerializedName("genre_ids")         val genreIds:         List<Int> = listOf(),
        @SerializedName("original_title")    val originalTitle:    String,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("backdrop_path")     val backdropPath:     String? = null,
        @SerializedName("vote_count")        val voteCount:        Int,
        @SerializedName("vote_average")      val voteAverage:      Int,
    )
}
