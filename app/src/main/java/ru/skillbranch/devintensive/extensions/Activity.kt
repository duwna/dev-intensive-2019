package ru.skillbranch.devintensive.extensions

import android.app.Activity

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Context.hideKeyboard(view: View?) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}


fun Activity.isKeyboardOpen(): Boolean {
    val r = Rect()
    this.window.decorView.getWindowVisibleDisplayFrame(r)
    return this.windowManager.defaultDisplay.height - (r.bottom - r.top) > 100
}

fun Activity.isKeyboardClosed() = !isKeyboardOpen()
