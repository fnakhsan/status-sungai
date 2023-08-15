package uin.suka.status.sungai.ui.components

import android.content.Context
import android.widget.Toast
import uin.suka.status.sungai.core.utils.UiText

object Toast {
    fun errorToast(context: Context, error: UiText) {
        Toast.makeText(
            context,
            error.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }
}