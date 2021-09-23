package com.skyyo.navigationissue.features.tab3

import android.util.Log
import androidx.lifecycle.ViewModel
import com.skyyo.navigationissue.LOGGING_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class Tab3ViewModel @Inject constructor(): ViewModel() {

    init {
        Log.d(LOGGING_TAG, "Tab3 viewModel: $this")
    }
}
