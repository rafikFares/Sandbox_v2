package com.example.uibox.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.uibox.databinding.ViewLoginPasswordBinding
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.toString

class LoginPasswordView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ViewLoginPasswordBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = VERTICAL
    }

    val userNameText: String
        get() = "${binding.userNameField.text}"

    val passwordText: String
        get() = "${binding.passwordField.text}"

    fun showErrorState(hint: StringSource) {
        binding.passwordContainer.error = hint.toString(context)
    }
}
