package com.jgt.wizelinebaz2023.presentation.states

import com.jgt.wizelinebaz2023.core.mvi.ProductionRule
import com.jgt.wizelinebaz2023.core.mvi.State
import com.jgt.wizelinebaz2023.presentation.actions.MoviesComponentsActions

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 20/04/23.
 * * * * * * * * * * **/
data class MovieListCategoryState( val category: String ): State() {
    override val productionRules: List<ProductionRule> = listOf { state, action ->
        if( action is MoviesComponentsActions.SetCategoryAction )
            MovieListCategoryState( action.categoryName )
        else
            state
    }
}
