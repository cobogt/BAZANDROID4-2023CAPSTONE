package com.jgt.wizelinebaz2023.core.mvi

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
abstract class Action {
    object VoidAction: Action()
    object LoadStateAction: Action()
    data class ErrorAction(val text: String, val exception: Exception? = null): Action()
}
