package com.zemoga.ui.util

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun View.visible(bool: Boolean = true) {
    visibility = if (!bool) View.INVISIBLE else View.VISIBLE
}

fun View.enable(bool: Boolean? = true) {
    isEnabled = bool ?: false
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, function: (T) -> Unit) {
    observe(owner, androidx.lifecycle.Observer { function(it) })
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, function: (T) -> Unit) {
    liveData.observe(this, androidx.lifecycle.Observer { function(it) })
}