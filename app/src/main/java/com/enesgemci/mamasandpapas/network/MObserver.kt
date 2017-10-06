/*
 * Copyright (c) 2017.
 *
 *      "Therefore those skilled at the unorthodox
 *        are infinite as heaven and earth,
 *        inexhaustible as the great rivers.
 *        When they come to an end,
 *        they begin again,
 *        like the days and months;
 *        they die and are reborn,
 *        like the four seasons."
 *
 *     - Sun Tsu, "The Art of War"
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.network

import io.reactivex.observers.DisposableObserver
import timber.log.Timber

/**
 * Created by enesgemci on 29/05/2017.
 */

open class MObserver<T> : DisposableObserver<T>() {

    override fun onComplete() {
    }

    override fun onError(t: Throwable) {
        Timber.e(t)
    }

    override fun onNext(t: T) {}
}