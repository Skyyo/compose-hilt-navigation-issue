package com.skyyo.navigationissue

import androidx.annotation.StringRes

sealed class Destination(val route: String, @StringRes val resourceId: Int = 0) {

    object Tab1 : Destination("tab1", R.string.tab1)
    object Tab2 : Destination("tab2", R.string.tab2)
    object Tab3 : Destination("tab3", R.string.tab3)

}