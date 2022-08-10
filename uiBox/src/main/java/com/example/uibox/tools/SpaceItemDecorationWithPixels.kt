package com.example.uibox.tools

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class SpaceItemDecorationWithPixels(
    private val topPX: Int = 0,
    private val leftPX: Int = 0,
    private val rightPX: Int = 0,
    private val bottomPX: Int = 0,
    private val firstTopPX: Int = 0,
    private val firstLeftPX: Int = 0,
    private val firstRightPX: Int = 0,
    private val firstBottomPX: Int = 0,
    private val lastTopPX: Int = 0,
    private val lastLeftPX: Int = 0,
    private val lastRightPX: Int = 0,
    private val lastBottomPX: Int = 0
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        when (parent.getChildAdapterPosition(view)) {
            0 -> { // First item
                outRect.top = (firstTopPX + topPX)
                outRect.right = (firstRightPX + rightPX)
                outRect.left = (firstLeftPX + leftPX)
                outRect.bottom = (firstBottomPX + bottomPX)
            }
            parent.adapter?.itemCount?.minus(1) -> { // Last item
                outRect.top = (lastTopPX + topPX)
                outRect.right = (lastRightPX + rightPX)
                outRect.left = (lastLeftPX + leftPX)
                outRect.bottom = (lastBottomPX + bottomPX)
            }
            else -> {
                outRect.top = topPX
                outRect.right = rightPX
                outRect.left = leftPX
                outRect.bottom = bottomPX
            }
        }
    }
}
