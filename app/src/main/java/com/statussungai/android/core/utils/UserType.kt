package com.statussungai.android.core.utils

enum class UserType(val type: String) {
    GUEST("guest"),
    VOLUNTEER("relawan"),
    ADMIN("admin");

    companion object {
        fun getUserTypeByType(type: String) = when (type) {
            "relawan" -> VOLUNTEER
            "admin" -> ADMIN
            else -> GUEST
        }

        fun getTypeByUserType(userType: UserType) = when (userType) {
            VOLUNTEER -> VOLUNTEER.type
            ADMIN -> ADMIN.type
            else -> GUEST.type
        }
    }
}