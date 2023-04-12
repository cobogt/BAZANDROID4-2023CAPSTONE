package com.jgt.wizelinebaz2023.storage.local.room.entities.base

import androidx.room.Entity
import androidx.room.PrimaryKey

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
@Entity("categories")
data class CategoriesTable(
    @PrimaryKey
    val id:   Int,
    val name: String,
)
