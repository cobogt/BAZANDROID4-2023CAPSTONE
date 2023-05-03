package com.jgt.core.mvi

import com.jgt.core.gates.Gate

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
abstract class Action {
    open var gate: Gate? = null

    object VoidAction: Action()
    object LoadStateAction: Action()
    data class ErrorAction(val text: String, val exception: Exception? = null): Action()
}
