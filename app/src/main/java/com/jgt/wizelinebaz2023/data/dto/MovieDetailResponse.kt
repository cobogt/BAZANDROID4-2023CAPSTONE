package com.jgt.wizelinebaz2023.data.dto

import com.google.gson.annotations.SerializedName
import com.jgt.wizelinebaz2023.domain.models.MovieDetail
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.MoviesTable

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
data class MovieDetailResponse(
                                            val id:                   Int,
                                            val adult:                Boolean,
                                            val budget:               Int,
                                            val genres:               List<Genre> = listOf(),
                                            val homepage:             String? = null,
                                            val overview:             String? = null,
                                            val popularity:           Float,
                                            val revenue:              Int,
                                            val runtime:              Int? = null,
                                            val status:               String,
                                            val tagline:              String? = null,
                                            val title:                String,
                                            val video:                Boolean,
    @SerializedName("imdb_id")              val imdbId:               String? = null,
    @SerializedName("backdrop_path")        val backdropPath:         String? = null,
    @SerializedName("original_language")    val originalLanguage:     String,
    @SerializedName("original_title")       val originalTitle:        String,
    @SerializedName("poster_path")          val posterPath:           String? = null,
    @SerializedName("production_companies") val productionCompanies:  List<ProductionCompany> = listOf(),
    @SerializedName("production_countries") val productionCountries:  List<ProductionCountry> = listOf(),
    @SerializedName("spoken_languages")     val spokenLanguages:      List<SpokenLanguage>    = listOf(),
    @SerializedName("release_date")         val releaseDate:          String,
    @SerializedName("vote_average")         val voteAverage:          Float,
    @SerializedName("vote_count")           val voteCount:            Int,
) {
    data class Genre (
        val id:   Int,
        val name: String,
    )

    data class ProductionCompany(
        val id:            Int,
        val name:          String,
        val logoPath:      String? = null,
        val originCountry: String,
    )

    data class ProductionCountry(
        val name: String,
        @SerializedName("iso_3166_1") val iso3366i: String,
    )

    data class SpokenLanguage(
        val name: String,
        @SerializedName("iso_3166_1") val iso3366i: String,
    )

    fun toModel() = MovieDetail(
        id          = id,
        name        = title,
        imageUrl    = "https://image.tmdb.org/t/p/original/${posterPath}",
        releaseDate = releaseDate,
        status      = status,
        budget      = budget,
        adult       = adult
    )
}
