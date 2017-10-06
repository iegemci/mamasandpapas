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

package com.enesgemci.mamasandpapas.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.view.View;
import android.view.Window;

import com.enesgemci.mamasandpapas.R;

/**
 * Created by enesgemci on 04/11/15.
 */
@Keep
public class MProgressDialog extends Dialog {

    private MLoadingView mLoadingView;

    private boolean isCancelable;

    public MProgressDialog(Context context) {
        super(context);
        isCancelable = true;
    }

    public MProgressDialog(Context context, OnCancelListener onCancelListener) {
        super(context);
        setOnCancelListener(onCancelListener);
        isCancelable = onCancelListener != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view = getLayoutInflater().inflate(R.layout.loading_view, null);
        setContentView(view);
        setCancelable(isCancelable);
        setCanceledOnTouchOutside(false);

        mLoadingView = view.findViewById(R.id.layoutProgressable_ivProgress);
    }
}