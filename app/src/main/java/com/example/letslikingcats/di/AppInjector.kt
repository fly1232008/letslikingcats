package com.example.letslikingcats.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.letslikingcats.App
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

object AppInjector {

    private lateinit var appComponent: AppComponent

    fun init(app: App) {
        appComponent = DaggerAppComponent.builder().application(app)
            .build()
        appComponent.inject(app)
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                //ignored
            }

            override fun onActivityResumed(activity: Activity) {
                //ignored
            }

            override fun onActivityPaused(activity: Activity) {
                //ignored
            }

            override fun onActivityStopped(activity: Activity) {
                //ignored
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                //ignored
            }

            override fun onActivityDestroyed(activity: Activity) {
                //ignored
            }

        })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasAndroidInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentPreCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            super.onFragmentPreCreated(fm, f, savedInstanceState)
                            if (f is HasInject) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true
                )
        }
    }

}