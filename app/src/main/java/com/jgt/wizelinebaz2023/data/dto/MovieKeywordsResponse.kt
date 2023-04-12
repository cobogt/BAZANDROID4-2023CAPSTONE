package com.jgt.wizelinebaz2023.data.dto

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
}
