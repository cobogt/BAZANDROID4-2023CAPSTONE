package com.jgt.wizelinebaz2023.storage.local.room.entities.crossref

import androidx.room.Entity
import androidx.room.Index

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
@Entity(
    tableName = "movies_keywords",
    primaryKeys = ["movieId", "keywordId"],
    indices = [
        Index("movieId", unique = true),
        Index("keywordId", unique = true)
    ])
data class MoviesKeywordsCrossRef(
    val movieId:   Int,
    val keywordId: Int
)
