/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.base

import android.support.multidex.MultiDexApplication
import com.enesgemci.mamasandpapas.BuildConfig
import com.enesgemci.mamasandpapas.dagger.component.AppComponent
import com.enesgemci.mamasandpapas.dagger.component.DaggerAppComponent
import com.enesgemci.mamasandpapas.dagger.module.AppModule
import com.enesgemci.mamasandpapas.dagger.module.DataModule
import com.google.gson.Gson
import dagger.Lazy
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by enesgemci on 09/09/15.
 */
internal class MApp : MultiDexApplication() {

    @Inject
    lateinit internal var gson: Lazy<Gson>

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .dataModule(DataModule(BuildConfig.HOST))
                .build()
        appComponent!!.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onTerminate() {
        appComponent = null
        super.onTerminate()
    }

    companion object {

        @JvmStatic
        var appComponent: AppComponent? = null
            private set
    }
}