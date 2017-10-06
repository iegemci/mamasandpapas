package com.enesgemci.mamasandpapas.util

import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enesgemci on 05/10/2017.
 */
@Singleton
class MExecutor @Inject constructor() {

    private val executor: ExecutorService = Executors.newCachedThreadPool()

    fun execute(runnable: Runnable) {
        executor.execute(runnable)
    }

    fun destroy() {
        try {
            executor.shutdown()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}