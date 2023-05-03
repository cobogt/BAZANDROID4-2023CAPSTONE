package com.jgt.core.gates

import com.jgt.core.mvi.Action

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 25/04/23.
 * * * * * * * * * * **/
data class ExitGateAction(
    val result: GateResult
): Action()
