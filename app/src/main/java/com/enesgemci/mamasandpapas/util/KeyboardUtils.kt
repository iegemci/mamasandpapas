package com.enesgemci.mamasandpapas.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by enesgemci on 06/10/2017.
 */
object KeyboardUtils {

    /**
     * Klavyeyi gösterir

     * @param context
     */
    fun toggle(context: Context) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        } catch (e: Exception) {
        }
    }

    fun toggle(context: Context, editText: EditText) {
        editText.requestFocus()
        editText.postDelayed({ toggle(context) }, 400)
    }

    fun show(editText: EditText?) {
        try {
            val imm = editText!!.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT, object : ResultReceiver(Handler()) {

                override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                    super.onReceiveResult(resultCode, resultData)

                    if (resultCode != InputMethodManager.RESULT_SHOWN) {
                        show(editText)
                    }
                }
            })
        } catch (e: Exception) {
        }
    }

    fun hide(context: Context) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow((context as Activity).currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
        }
    }
}