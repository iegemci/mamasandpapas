/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.network

import android.os.Build
import android.provider.Settings
import android.support.annotation.Keep
import com.enesgemci.mamasandpapas.BuildConfig
import com.enesgemci.mamasandpapas.base.MApp
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enesgemci on 29/06/16.
 */
@Keep
@Singleton
internal class HeaderInterceptor @Inject constructor(internal var application: MApp) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        try {
            val buffer = Buffer()
            val body: String

            body = if (request.body() != null) {
                request.body()!!.writeTo(buffer)
                buffer.readUtf8()
            } else {
                "encoded path -> " + request.url().encodedPath()
            }

            Timber.wtf("---> [MREQUEST] : " + body)
        } catch (e: Exception) {
        }

        var builder: Request.Builder = request.newBuilder()
                .header("OsType", "ANDROID")
                .header("AppVersion", BuildConfig.VERSION_NAME)
                .header("DeviceUnique", Settings.Secure.getString(application.contentResolver, Settings.Secure.ANDROID_ID))
                .header("DeviceInfo", Build.MANUFACTURER + "," + Build.MODEL + "," + Build.VERSION.RELEASE + "," + Build.VERSION.SDK_INT.toString())
                .cacheControl(CacheControl.FORCE_NETWORK)
                .method(request.method(), request.body())

        val newReq = builder.build()

        Timber.e("[METHOD] : " + newReq.method())
        Timber.e(String.format("[MREQUEST HEADER] Sending request %s on %s%n%s", newReq.url(), chain.connection(), newReq.headers()))

        val t1 = System.nanoTime()
        val response = chain.proceed(newReq)
        val t2 = System.nanoTime()
        val elapsedTime = (t2 - t1) / 1e6

        Timber.e(String.format("[MRESPONSE] Received response for %s in %.1fms%n%s", response.request().url(), elapsedTime, response.headers()))

        return response
    }
}