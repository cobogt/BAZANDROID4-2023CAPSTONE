package com.jgt.content.movies.storage.local.room.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jgt.content.movies.domain.models.MovieDetail
import com.jgt.content.movies.domain.models.MovieImages
import com.jgt.content.movies.domain.models.MovieKeyword
import com.jgt.content.movies.storage.local.room.entities.base.CategoriesTable
import com.jgt.content.movies.storage.local.room.entities.base.ImagesTable
import com.jgt.content.movies.storage.local.room.entities.base.KeywordsTable
import com.jgt.content.movies.storage.local.room.entities.base.MoviesTable
import com.jgt.content.movies.storage.local.room.entities.crossref.MoviesCategoriesCrossRef

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/

data class MovieWithDetails(
    @Embedded
    val movie: MoviesTable,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
    )
    val images: List<ImagesTable>,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
    )
    val keywords: List<KeywordsTable>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction( MoviesCategoriesCrossRef::class,
            parentColumn = "movieId",
            entityColumn = "categoryId"
        )
    )
    val categories: List<CategoriesTable>,
) {
    fun toModel() = MovieDetail(
        id          = movie.id,
        name        = movie.title,
        status      = movie.status,
        overview    = movie.overview,
        imageUrl    = "${movie.posterPath}",
        keywords    = keywords.map {
            MovieKeyword(it.id, it.keyword)
        },
        images = MovieImages(
            posters = images.filter{ it.isPoster == 1 }.map {
                MovieImages.MovieImage(it.id, it.path, it.height, it.width, it.votes, it.voteAverage)
            },
            backdrops = images.filter{ it.isBackdrop == 1 }.map {
                MovieImages.MovieImage(it.id, it.path, it.height, it.width, it.votes, it.voteAverage)
            },
        )
    )
}
