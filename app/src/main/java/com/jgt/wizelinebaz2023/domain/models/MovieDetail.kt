package com.jgt.wizelinebaz2023.domain.models

import com.jgt.wizelinebaz2023.storage.local.room.entities.base.MoviesTable

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 18/04/23.
 * * * * * * * * * * **/
data class MovieDetail(

    val id:               Int     = 0,
    val name:             String  = "",
    val imageUrl:         String  = "",
    val releaseDate:      String  = "",
    val status:           String  = "",
    val budget:           Int     = 0,
    val adult:            Boolean = false,
    val homepage:         String? = null,
    val overview:         String? = null,
    val popularity:       Float   = 0F,
    val revenue:          Int     = 0,
    val runtime:          Int?    = null,
    val video:            Boolean = false,
    val imdbId:           String? = null,
    val backdropPath:     String? = null,
    val originalLanguage: String  = "",
    val originalTitle:    String  = "",
    val voteAverage:      Float   = 0F,
    val voteCount:        Int     = 0,

    val images:      MovieImages        = MovieImages(),
    val keywords:    List<MovieKeyword> = listOf(),
) {
    fun toEntity() = MoviesTable(
        id               = id,
        adult            = adult,
        budget           = budget,
        homepage         = homepage,
        overview         = overview,
        popularity       = popularity,
        revenue          = revenue,
        runtime          = runtime,
        status           = status,
        title            = name,
        video            = video,
        imdbId           = imdbId,
        backdropPath     = backdropPath,
        originalLanguage = originalLanguage,
        originalTitle    = originalTitle,
        posterPath       = imageUrl,
        releaseDate      = releaseDate,
        voteAverage      = voteAverage,
        voteCount        = voteCount,
    )
}
