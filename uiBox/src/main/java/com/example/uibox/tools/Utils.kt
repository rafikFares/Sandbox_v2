package com.example.uibox.tools

import android.content.Context
import android.content.res.Resources
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


fun Int.fromDpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()


fun View.GlideCacheLoad(url: String) = context.GlideCacheLoad(url)

fun Context.GlideCacheLoad(url: String) =
    Glide
        .with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .placeholder(android.R.drawable.stat_sys_download)

