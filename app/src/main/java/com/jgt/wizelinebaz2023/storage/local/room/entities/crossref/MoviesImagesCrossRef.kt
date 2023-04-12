package com.jgt.wizelinebaz2023.storage.local.room.entities.crossref

import androidx.room.Entity
import androidx.room.Index

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
@Entity(
    tableName = "movies_images",
    primaryKeys = ["movieId", "imageId"],
    indices = [
        Index("movieId", unique = true),
        Index("imageId", unique = true)
    ])
data class MoviesImagesCrossRef(
    val movieId: Int,
    val imageId: Int,
)
