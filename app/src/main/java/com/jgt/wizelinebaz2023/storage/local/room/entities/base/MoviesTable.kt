package com.jgt.wizelinebaz2023.storage.local.room.entities.base

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jgt.wizelinebaz2023.domain.models.MovieDetail

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
@Entity("movies")
data class MoviesTable(
    @PrimaryKey
    val id:               Int,
    val adult:            Boolean,
    val budget:           Int,
    val homepage:         String? = null,
    val overview:         String? = null,
    val popularity:       Float,
    val revenue:          Int,
    val runtime:          Int? = null,
    val status:           String,
    val title:            String,
    val video:            Boolean,
    val imdbId:           String? = null,
    val backdropPath:     String? = null,
    val originalLanguage: String,
    val originalTitle:    String,
    val posterPath:       String? = null,
    val releaseDate:      String,
    val voteAverage:      Float,
    val voteCount:        Int,
) {
    fun toModel() = MovieDetail(
        id               = id,
        name             = title,
        imageUrl         = "$posterPath",
        releaseDate      = releaseDate,
        status           = status,
        budget           = budget,
        adult            = adult,
        homepage         = homepage,
        overview         = overview,
        popularity       = popularity,
        revenue          = revenue,
        runtime          = runtime,
        video            = video,
        imdbId           = imdbId,
        backdropPath     = backdropPath,
        originalLanguage = originalLanguage,
        originalTitle    = originalTitle,
        voteAverage      = voteAverage,
        voteCount        = voteCount,
    )
}
