/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.base.mvp

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpPresenter

/**
 * Created by enesgemci on 29/11/2016.
 */

internal abstract class BaseMvpPresenter<V : BaseMvpView> : MvpBasePresenter<V>(), MvpPresenter<V>