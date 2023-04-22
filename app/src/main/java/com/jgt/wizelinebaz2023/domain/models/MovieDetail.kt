package com.jgt.wizelinebaz2023.domain.models

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 18/04/23.
 * * * * * * * * * * **/
data class MovieDetail(
    val id:          Int     = 0,
    val name:        String  = "",
    val imageUrl:    String  = "",
    val releaseDate: String  = "",
    val status:      String  = "",
    val budget:      Int     = 0,
    val adult:       Boolean = false,
)
