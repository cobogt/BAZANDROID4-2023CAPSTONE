package com.jgt.wizelinebaz2023.storage.local.room.entities.crossref

import androidx.room.Entity
import androidx.room.Index

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
@Entity(tableName = "movies_categories",
    primaryKeys = ["movieId", "categoryId"],
    indices = [
        Index("movieId", unique = true),
        Index("categoryId", unique = true)
    ]
)
data class MoviesCategoriesCrossRef(
    val movieId:    Int,
    val categoryId: Int
)
