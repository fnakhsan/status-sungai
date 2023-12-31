package uin.suka.status.sungai.ui.components

import android.content.Context
import android.widget.Toast
import com.example.core.utils.UiText
import com.example.core.utils.UiText.Companion.asString

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.errorToast(error: UiText) =
    Toast.makeText(this, error.asString(this), Toast.LENGTH_SHORT).show()