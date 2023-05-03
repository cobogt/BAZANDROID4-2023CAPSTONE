package com.jgt.content.movies.data.dto

import com.jgt.content.movies.domain.models.MovieKeyword

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
data class MovieKeywordsResponse(
    val id: Int,
    val keywords: List<KeyWord> = listOf()
) {
    data class KeyWord(
        val id: Int,
        val name: String,
    )

    fun toModel() =
        keywords.map {
            MovieKeyword( it.id, it.name )
        }
}
