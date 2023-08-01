package uin.suka.status.sungai.data

import uin.suka.status.sungai.core.utils.UiText

sealed class Resource<out R> private constructor(){
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val error: UiText): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}