package ru.mingaleev.weatherandroidkotlin.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class Utils {
}

fun createAndShowSnackbar (view: View, text: String, actionText: String, duration: Int, action: (v: View) -> Unit) {
    Snackbar.make(view, text, duration).setAction(actionText, action).show() }