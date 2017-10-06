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

import com.enesgemci.mamasandpapas.base.MApp
import com.enesgemci.mamasandpapas.network.AnnotationExclusionStrategy
import com.enesgemci.mamasandpapas.network.HeaderInterceptor
import com.enesgemci.mamasandpapas.network.MRestService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.otto.Bus
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by enesgemci on 11/05/2017.
 */
@Module
internal class DataModule(internal var mBaseUrl: String) {

    @Provides
    @Singleton
    fun provideHttpCache(application: MApp): Cache {
        var cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
                .setExclusionStrategies(AnnotationExclusionStrategy())
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideBus(): Bus {
        return Bus()
    }

    @Provides
    @Singleton
    internal fun provideHeaderInterceptor(application: MApp): HeaderInterceptor {
        return HeaderInterceptor(application)
    }

    @Provides
    @Singleton
    internal fun provideOkhttpClient(headerInterceptor: HeaderInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(15000, TimeUnit.MILLISECONDS)
                .readTimeout(15000, TimeUnit.MILLISECONDS)
                .writeTimeout(15000, TimeUnit.MILLISECONDS)
                .cache(cache)
                .addNetworkInterceptor(headerInterceptor)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    internal fun provideApiService(retrofit: Retrofit): MRestService {
        return retrofit.create(MRestService::class.java)
    }
}