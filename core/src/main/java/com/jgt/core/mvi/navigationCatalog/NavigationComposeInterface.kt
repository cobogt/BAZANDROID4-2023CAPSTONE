package com.jgt.core.mvi.navigationCatalog

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 17/04/23.
 * * * * * * * * * * **/
interface NavigationComposeInterface: NavigationTarget {
    val activity: NavigationActivityInterface
    val path:     String
    val params:   Map<String, String>
}
