package com.jgt.content.movies.presentation.actions

import com.jgt.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 17/04/23.
 * * * * * * * * * * **/
sealed class MoviesComponentsActions: com.jgt.core.mvi.Action() {
    data class SetCategoryAction( val categoryName: String ): MoviesComponentsActions()
}
