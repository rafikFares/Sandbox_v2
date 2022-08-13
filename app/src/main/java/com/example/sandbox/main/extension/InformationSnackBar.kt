package com.example.sandbox.main.extension

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

/**
 * show information views as a top snack bar
 * this will help to wrap any custom view into a notification
 * can be used like :
 * val customView = CustomView(context)
 * customView.bind(data) ...
 * customView..showAsInformationSnackBar()
 */
fun View.showAsInformationSnackBar(
    parentView: View,
    @BaseTransientBottomBar.Duration duration: Int = BaseTransientBottomBar.LENGTH_LONG
) {
    val notification = Snackbar.make(parentView, "", duration)
    val background = ContextCompat.getColor(this.context, android.R.color.transparent)

    val notificationLayout = notification.view as Snackbar.SnackbarLayout
    notificationLayout.addView(this)
    notificationLayout.setBackgroundColor(background)
    val params = notificationLayout.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.TOP
    (notificationLayout as View).layoutParams = params
    notification.show()
}
