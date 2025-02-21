package com.statussungai.android.data

import com.statussungai.android.core.utils.UiText

sealed class Resource<out R> private constructor(){
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val error: UiText): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}