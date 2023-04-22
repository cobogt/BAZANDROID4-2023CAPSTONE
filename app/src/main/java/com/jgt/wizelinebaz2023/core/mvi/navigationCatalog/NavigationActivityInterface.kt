package com.jgt.wizelinebaz2023.core.mvi.navigationCatalog

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 17/04/23.
 * * * * * * * * * * **/
interface NavigationActivityInterface: NavigationTarget {
    val className: String
    val params: Map<String, String>
}
