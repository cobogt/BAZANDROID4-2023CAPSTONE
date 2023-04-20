package com.jgt.wizelinebaz2023.presentation.actions

import com.jgt.wizelinebaz2023.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 20/04/23.
 * * * * * * * * * * **/
sealed class RefreshRepositoryActions: Action() {
    object RefreshMovieList: RefreshRepositoryActions()
}
