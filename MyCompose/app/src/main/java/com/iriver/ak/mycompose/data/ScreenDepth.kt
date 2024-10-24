package com.iriver.ak.mycompose.data

import androidx.annotation.StringRes
import com.iriver.ak.mycompose.R

enum class MyScreenDepth(@StringRes val title: Int) {
    DEPTH1(title = R.string.depth1), DEPTH2(title = R.string.depth2), DEPTH3(title = R.string.depth3), DEPTH4(
        title = R.string.depth4
    )
}