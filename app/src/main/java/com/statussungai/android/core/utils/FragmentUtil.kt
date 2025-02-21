package com.statussungai.android.core.utils

enum class FragmentUtil(val id: Int, val title: String) {
    MAPS(0, "Maps"),
    ADD(1, "Add"),
    PROFILE(2, "Profile");

    companion object {
        fun getFragmentTitleById(id: Int) = when (id) {
            MAPS.ordinal -> MAPS.title
            ADD.ordinal -> ADD.title
            PROFILE.ordinal -> PROFILE.title
            else -> MAPS.title
        }
    }
}