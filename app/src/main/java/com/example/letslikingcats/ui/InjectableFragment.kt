package com.example.letslikingcats.ui

import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import moxy.MvpAppCompatFragment
import javax.inject.Inject

abstract class InjectableFragment @ContentView constructor(@LayoutRes layout:  Int):
    MvpAppCompatFragment(layout), HasAndroidInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = fragmentInjector
}