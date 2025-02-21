package com.statussungai.android.ui.components

import android.content.Context
import android.widget.Toast
import com.statussungai.android.core.utils.UiText
import com.statussungai.android.core.utils.UiText.Companion.asString

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.errorToast(error: UiText) =
    Toast.makeText(this, error.asString(this), Toast.LENGTH_SHORT).show()