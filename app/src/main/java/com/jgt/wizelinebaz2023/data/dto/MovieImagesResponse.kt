package com.jgt.wizelinebaz2023.data.dto

import com.google.gson.annotations.SerializedName
import com.jgt.wizelinebaz2023.domain.models.MovieImages

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
data class MovieImagesResponse(
    val id: Int,
    val backdrops: List<Image> = listOf(),
    val posters:   List<Image> = listOf(),
) {
    data class Image(
        val height:       Int,
        val width:        Int,
        @SerializedName("aspect_ratio") val aspectRatio: Float,
        @SerializedName("file_path")    val filePath:    String,
        @SerializedName("iso_639_1")    val iso639i:     String? = null,
        @SerializedName("vote_average") val voteAverage: Float,
        @SerializedName("vote_count")   val voteCount:   Int,
    )

    fun toModel() = MovieImages(
        posters = posters.map {
            MovieImages.MovieImage( id, it.filePath, it.height, it.width, it.voteCount, it.voteAverage )
        },
        backdrops = backdrops.map {
            MovieImages.MovieImage( id, it.filePath, it.height, it.width, it.voteCount, it.voteAverage )
        }
    )
}
