/*
 * Copyright (c) 2016.
 *
 * Enes Gemci
 */

package com.enesgemci.mamasandpapas.util.fragment

import java.io.Serializable

/**
 * Created by enesgemci on 15/09/15.
 */
enum class TransactionAnimation : Serializable {

    NO_ANIM,
    ENTER_FROM_LEFT,
    ENTER_FROM_RIGHT,
    ENTER_FROM_BOTTOM,
    ENTER_WITH_ALPHA,
    ENTER_FROM_RIGHT_STACK,
    ENTER_FROM_RIGHT_NO_ENTRANCE
}