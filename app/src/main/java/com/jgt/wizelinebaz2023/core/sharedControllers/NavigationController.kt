package com.jgt.wizelinebaz2023.core.sharedControllers

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.jgt.wizelinebaz2023.core.BaseApplication
import com.jgt.wizelinebaz2023.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.wizelinebaz2023.core.sharedActions.NavigationActions

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 13/04/23.
 * * * * * * * * * * **/

@SuppressLint("StaticFieldLeak")
object NavigationController: Application.ActivityLifecycleCallbacks {
    private var currentActivity: Activity? = null

    fun getCurrentActivityName(): String? = currentActivity?.javaClass?.canonicalName

    fun finishCurrentActivity() {
        currentActivity?.finish()
    }

    fun navigateToActivity(
        className: String,
        params: Map<String, String> = mapOf()
    ) {
        if( currentActivity != null && currentActivity!!.javaClass.canonicalName == className )
            currentActivity?.intent = Intent().apply {
                params.forEach {
                    putExtra( it.key, it.value )
                }
            }
        else
            launchActivity( className, params )
    }

    private fun launchActivity(
        className: String,
        params: Map<String, String> = mapOf()
    ) {
        BaseApplication.appContext.startActivity(
            Intent().apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP + Intent.FLAG_ACTIVITY_NEW_TASK

                params.forEach {
                    putExtra( it.key, it.value )
                }

                setClassName( BaseApplication.appContext, className )
            }
        )
    }

    fun navigateToCompose( composePath: String ) {
        currentActivity
            ?.takeIf { it is ActivityWithViewModelStoreInterface }
            ?.let { it as ActivityWithViewModelStoreInterface }
            ?.also {
                it.viewModelStateStore.dispatch(
                    NavigationActions.NavigateToCompose( composePath )
                )
            }
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
