package com.jgt.wizelinebaz2023.core

import android.app.Application
import android.content.Context
import com.jgt.wizelinebaz2023.core.sharedControllers.NavigationController

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
class BaseApplication: Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        registerActivityLifecycleCallbacks( NavigationController )
    }
}