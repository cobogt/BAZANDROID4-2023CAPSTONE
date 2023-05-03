package com.jgt.content.movies.storage.local.room.entities.base

import androidx.room.Entity
import androidx.room.PrimaryKey

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
@Entity("categories")
data class CategoriesTable(
    @PrimaryKey( autoGenerate = true )
    val id:   Int    = 0,
    val name: String,
)
