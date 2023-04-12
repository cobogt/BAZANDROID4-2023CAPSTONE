package com.jgt.wizelinebaz2023.storage.local.room.entities.base

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/
@Entity("images")
data class ImagesTable(
    @PrimaryKey
    val id:                                             Int,
    val height:                                         Int,
    val width:                                          Int,
    @ColumnInfo("aspect_ratio") val aspectRatio: Int,
    @ColumnInfo("file_path")    val filePath:    String,
    @ColumnInfo("iso_639_1")    val iso639i:     String? = null,
    @ColumnInfo("vote_average") val voteAverage: Int,
    @ColumnInfo("vote_count")   val voteCount:   Int,
)
