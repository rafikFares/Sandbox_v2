package com.example.uibox.tools

class SpaceItemDecoration(
    topDP: Int = 0,
    leftDP: Int = 0,
    rightDP: Int = 0,
    bottomDP: Int = 0,
    firstTopDP: Int = 0,
    firstLeftDP: Int = 0,
    firstRightDP: Int = 0,
    firstBottomDP: Int = 0,
    lastTopDP: Int = 0,
    lastLeftDP: Int = 0,
    lastRightDP: Int = 0,
    lastBottomDP: Int = 0
) : SpaceItemDecorationWithPixels(
    topPX = topDP.fromDpToPx(),
    leftPX = leftDP.fromDpToPx(),
    rightPX = rightDP.fromDpToPx(),
    bottomPX = bottomDP.fromDpToPx(),
    firstTopPX = firstTopDP.fromDpToPx(),
    firstLeftPX = firstLeftDP.fromDpToPx(),
    firstRightPX = firstRightDP.fromDpToPx(),
    firstBottomPX = firstBottomDP.fromDpToPx(),
    lastTopPX = lastTopDP.fromDpToPx(),
    lastLeftPX = lastLeftDP.fromDpToPx(),
    lastRightPX = lastRightDP.fromDpToPx(),
    lastBottomPX = lastBottomDP.fromDpToPx()
)
