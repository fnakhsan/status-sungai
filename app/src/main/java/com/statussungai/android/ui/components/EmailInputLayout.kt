package com.statussungai.android.ui.components

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout

class EmailInputLayout : TextInputLayout {
    constructor(context: Context) : super(context) {
//        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
//        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
//        init()
    }

//    private fun init() {
//        editText?.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//                error =
//                    if (s.isBlank()) "This field cannot be empty" else null
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                error =
//                    if (s.isBlank()) "This field cannot be empty" else null
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                error =
//                    if (s?.isEmpty() == true) "This field cannot be empty" else null
//            }
//        })
//                if (s.toString().isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(s)
//                        .matches()
//                ) error = context.getString(R.string.invalid_email)
//    }
}