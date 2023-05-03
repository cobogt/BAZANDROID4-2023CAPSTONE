package com.jgt.content.movies.presentation.actions

import com.jgt.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 20/04/23.
 * * * * * * * * * * **/
sealed class RefreshRepositoryActions: com.jgt.core.mvi.Action() {
    object RefreshMovieList: RefreshRepositoryActions()
}
