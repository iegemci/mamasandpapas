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

package com.enesgemci.mamasandpapas.dagger.component

import android.content.Context
import com.enesgemci.mamasandpapas.base.MApp
import com.enesgemci.mamasandpapas.dagger.module.AppModule
import com.enesgemci.mamasandpapas.dagger.module.DataModule
import com.enesgemci.mamasandpapas.dagger.qualifier.ApplicationContext
import com.enesgemci.mamasandpapas.network.MRequestGenerator
import com.enesgemci.mamasandpapas.network.MRequestHandler
import com.enesgemci.mamasandpapas.network.MRestService
import com.google.gson.Gson
import com.squareup.otto.Bus
import dagger.Component
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by enesgemci on 08/04/2017.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class))
internal interface AppComponent {

    fun inject(mApp: MApp)

    fun inject(mRequestHandler: MRequestHandler)

    @get:ApplicationContext
    val applicationContext: Context

    val application: MApp

    fun provideHttpCache(): Cache

    fun provideGson(): Gson

    fun provideBus(): Bus

    fun provideOkhttpClient(): OkHttpClient

    fun provideRetrofit(): Retrofit

    fun provideApiService(): MRestService

    fun provideMRequestGenerator(): MRequestGenerator
}