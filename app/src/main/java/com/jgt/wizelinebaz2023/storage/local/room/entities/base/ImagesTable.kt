package com.jgt.wizelinebaz2023.storage.local.room.entities.base

import androidx.room.Entity
import androidx.room.PrimaryKey

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
@Entity("images")
data class ImagesTable(
    @PrimaryKey( autoGenerate = true )
    val id:          Int = 0,
    val movieId:     Int,
    val isPoster:    Int = 0,
    val isBackdrop:  Int = 0,
    val height:      Int,
    val width:       Int,
    val path:        String,
    val voteAverage: Float,
    val votes:       Int,
)
