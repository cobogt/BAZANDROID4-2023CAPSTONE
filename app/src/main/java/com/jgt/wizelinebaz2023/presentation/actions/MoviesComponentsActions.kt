package com.jgt.wizelinebaz2023.presentation.actions

import com.jgt.wizelinebaz2023.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 17/04/23.
 * * * * * * * * * * **/
sealed class MoviesComponentsActions: Action() {
    data class SetCategoryAction( val categoryName: String ): MoviesComponentsActions()
}