package com.jgt.wizelinebaz2023.storage.local.room.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.CategoriesTable
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.ImagesTable
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.KeywordsTable
import com.jgt.wizelinebaz2023.storage.local.room.entities.base.MoviesTable
import com.jgt.wizelinebaz2023.storage.local.room.entities.crossref.MoviesCategoriesCrossRef
import com.jgt.wizelinebaz2023.storage.local.room.entities.crossref.MoviesImagesCrossRef
import com.jgt.wizelinebaz2023.storage.local.room.entities.crossref.MoviesKeywordsCrossRef

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 12/04/23.
 * * * * * * * * * * **/

data class MovieWithDetails(
    @Embedded
    val movie: MoviesTable,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction( MoviesImagesCrossRef::class,
            parentColumn = "movieId",
            entityColumn = "imageId"
        )
    )
    val images: List<ImagesTable>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction( MoviesCategoriesCrossRef::class,
            parentColumn = "movieId",
            entityColumn = "categoryId"
        )
    )
    val categories: List<CategoriesTable>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction( MoviesKeywordsCrossRef::class,
            parentColumn = "movieId",
            entityColumn = "keywordId"
        )
    )
    val keywords: List<KeywordsTable>,
)
