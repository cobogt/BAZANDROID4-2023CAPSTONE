package com.jgt.wizelinebaz2023.storage.local.room.entities.base

import androidx.room.Entity
import androidx.room.PrimaryKey

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
@Entity("keywords")
data class KeywordsTable(
    @PrimaryKey
    val id:      Int = 0,
    val movieId: Int,
    val keyword: String,
)
