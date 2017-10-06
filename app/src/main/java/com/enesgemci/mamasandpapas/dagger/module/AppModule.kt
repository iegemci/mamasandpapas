/*
 * Copyright (c) 2017.
 *
 *     "Therefore those skilled at the unorthodox
 *      are infinite as heaven and earth,
 *      inexhaustible as the great rivers.
 *      When they come to an end,
 *      they begin again,
 *      like the days and months;
 *      they die and are reborn,
 *      like the four seasons."
 *
 * - Sun Tsu, "The Art of War"
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.dagger.module

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.enesgemci.mamasandpapas.base.MApp
import com.enesgemci.mamasandpapas.dagger.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Aditya on 23-Oct-16.
 */

@Module
internal class AppModule(internal var mApplication: MApp) {

    @Provides
    @Singleton
    internal fun provideApplication(): MApp {
        return mApplication
    }

    @Provides
    @Singleton
    @ApplicationContext
    internal fun provideApplicationContext(): Context {
        return mApplication.applicationContext
    }

    @Provides
    @Singleton
    internal fun provideConnectivityManager(): ConnectivityManager {
        return mApplication.getSystemService(Application.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
