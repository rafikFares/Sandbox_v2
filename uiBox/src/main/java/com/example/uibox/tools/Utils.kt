package com.example.uibox.tools

import android.content.res.Resources


fun Int.fromDpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

