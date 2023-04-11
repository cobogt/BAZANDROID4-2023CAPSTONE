package com.jgt.wizelinebaz2023.core.mvi

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
abstract class Caretaker<T: State> {
    abstract val defaultState: T
    abstract fun saveState( state: T )
    abstract fun loadState(): T?
}