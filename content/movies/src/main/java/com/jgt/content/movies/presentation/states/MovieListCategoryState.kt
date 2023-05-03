package com.jgt.content.movies.presentation.states

import com.jgt.core.mvi.ProductionRule
import com.jgt.core.mvi.State
import com.jgt.content.movies.presentation.actions.MoviesComponentsActions

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 20/04/23.
 * * * * * * * * * * **/
data class MovieListCategoryState( val category: String ): com.jgt.core.mvi.State() {
    override val productionRules: List<com.jgt.core.mvi.ProductionRule> = listOf { state, action ->
        if( action is MoviesComponentsActions.SetCategoryAction )
            MovieListCategoryState( action.categoryName )
        else
            state
    }
}
