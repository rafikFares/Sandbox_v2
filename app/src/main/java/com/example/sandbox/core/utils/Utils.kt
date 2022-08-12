package com.example.sandbox.core.utils

import kotlinx.datetime.Clock
fun String.Companion.empty() = ""

val currentTime: Long = Clock.System.now().epochSeconds
