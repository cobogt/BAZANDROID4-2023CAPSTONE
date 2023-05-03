package com.jgt.core.mvi

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
interface Caretaker {
    val defaultState: State
    fun<T: State> saveState(state: T )
    fun<T: State> loadState(): T?
}
