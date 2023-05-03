package com.jgt.core.sharedStates

import com.jgt.core.mvi.ProductionRule
import com.jgt.core.mvi.State
import com.jgt.core.sharedActions.NavigationActions

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 13/04/23.
 * * * * * * * * * * **/
sealed class NavigationState: State() {
    data class NavigateCompose( val path: String ): NavigationState() {
        override val productionRules: List<ProductionRule> = listOf {
            state, action ->
                if( action is NavigationActions.NavigateToCompose )
                    NavigateCompose( action.composePath )
                else
                    state
        }
    }
}
