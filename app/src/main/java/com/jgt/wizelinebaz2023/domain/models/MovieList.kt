package com.jgt.wizelinebaz2023.domain.models

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 18/04/23.
 * * * * * * * * * * **/
data class MovieList(
    val page: Int = 0,
    val movies: List<MovieDetail> = listOf()
)
