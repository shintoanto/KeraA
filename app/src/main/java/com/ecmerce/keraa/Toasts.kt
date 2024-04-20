package com.ecmerce.keraa

import android.content.Context
import android.util.Log
import android.widget.Toast

fun toast(context: Context, message: String, duration: Int) {
    return Toast.makeText(context, message, duration).show()
}

fun logs(tag: String, message: String, tr: Throwable): Int {
    return Log.d(tag, message, tr)
}