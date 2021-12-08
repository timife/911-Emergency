package com.timife.a911.extensions

import android.view.View
import androidx.core.view.isVisible

fun View.hide() {
    this.isVisible = false
}

fun View.show() {
    this.isVisible = true
}

fun View.enableClick() {
    this.isEnabled = true
}

fun View.disableClick() {
    this.isEnabled = false
}