package com.jgt.wizelinebaz2023.core.sharedControllers

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.jgt.wizelinebaz2023.core.BaseApplication

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 13/04/23.
 * * * * * * * * * * **/

@SuppressLint("StaticFieldLeak")
object NavigationController: Application.ActivityLifecycleCallbacks {
    private var currentActivity: Activity? = null

    fun navigateToActivity(
        className: String,
        params: Map<String, String> = mapOf()
    ) {
        if( currentActivity == null )
            launchActivity( className, params )
        else
            if( currentActivity!!.javaClass.canonicalName == className ) {

            }else{

            }

    }

    private fun launchActivity(
        className: String,
        params: Map<String, String> = mapOf()
    ) {
        BaseApplication.appContext.startActivity(
            Intent().apply {
                setClassName(
                    BaseApplication.appContext,
                    className
                )
            }
        )
    }

    fun navigateToCompose( composePath: String ) {

    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        currentActivity = p0
    }

    override fun onActivityStarted(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityResumed(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityPaused(p0: Activity) {
        currentActivity = null
    }

    override fun onActivityStopped(p0: Activity) {
        currentActivity = null
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        currentActivity = null
    }

    override fun onActivityDestroyed(p0: Activity) {
        currentActivity = null
    }
}