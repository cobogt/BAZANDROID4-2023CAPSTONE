package com.jgt.core.mvi.navigationCatalog

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 17/04/23.
 * * * * * * * * * * **/
sealed interface NavigationCatalog {
    data class AuthActivityTarget(
        override val params: Map<String, String> = mapOf()
    ): NavigationActivityInterface {
        override val className: String =
            "com.jgt.authentication.presentation.activities.AuthenticationActivity"
    }

    data class MoviesActivityTarget(
        override val params: Map<String, String> = mapOf()
    ): NavigationActivityInterface {
        override val className: String =
            "com.jgt.content.movies.presentation.activities.MoviesActivity"
    }

    data class LauncherActivityTarget(
        override val params: Map<String, String> = mapOf()
    ): NavigationActivityInterface {
        override val className: String =
            "com.jgt.wizelinebaz2023.LauncherActivity"
    }
}
