package com.jgt.content.movies.domain.models

import com.jgt.content.movies.storage.local.room.entities.base.ImagesTable

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 22/04/23.
 * * * * * * * * * * **/
data class MovieImages(
    val posters:   List<MovieImage> = listOf(),
    val backdrops: List<MovieImage> = listOf(),
) {
    data class MovieImage(
        val id:           Int,
        val path:         String,
        val height:       Int,
        val width:        Int,
        val votes:        Int,
        val votesAverage: Float,
    )

    fun toEntity( movieId: Int ): List<ImagesTable> =
        posters.map {
            ImagesTable(
                movieId     = movieId,
                isPoster    = 1,
                height      = it.height,
                width       = it.width,
                path        = it.path,
                voteAverage = it.votesAverage,
                votes       = it.votes,
            )
        }.plus(
            backdrops.map {
                ImagesTable(
                    movieId     = movieId,
                    isBackdrop  = 1,
                    height      = it.height,
                    width       = it.width,
                    path        = it.path,
                    voteAverage = it.votesAverage,
                    votes       = it.votes,
                )
            }
        )
}
