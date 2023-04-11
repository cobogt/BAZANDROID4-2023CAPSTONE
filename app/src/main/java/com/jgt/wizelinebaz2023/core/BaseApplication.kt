package com.jgt.wizelinebaz2023.core

import android.app.Application
import android.content.Context

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class BaseApplication: Application() {
    companion object {
        lateinit var AppContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        AppContext = applicationContext
    }
}