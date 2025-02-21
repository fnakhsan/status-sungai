package com.statussungai.android.core.utils

import androidx.core.widget.doAfterTextChanged

object TextIsNotBlankUtil {
    fun textIsNotBlankListener(editText: com.google.android.material.textfield.TextInputEditText) {
        editText.apply {
            error = if (text?.length == 0) "This field cannot be empty" else null
            doAfterTextChanged { s ->
                error = if (s?.length == 0) "This field cannot be empty" else null
            }
        }
    }
}