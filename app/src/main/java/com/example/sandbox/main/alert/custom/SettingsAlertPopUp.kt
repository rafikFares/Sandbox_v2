package com.example.sandbox.main.alert.custom

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.sandbox.R
import com.example.sandbox.core.repository.preference.PreferenceRepository
import com.example.sandbox.core.repository.preference.key.PreferenceKey
import com.example.uibox.databinding.ViewAlertSettingsBinding
import com.example.uibox.tools.clickWithDebounce
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class SettingsAlertPopUp : DialogFragment() {

    private val preferenceRepository: PreferenceRepository by inject()

    private var resultContinuation: Continuation<AlertPopUpResult>? = null

    private lateinit var customViewBinding: ViewAlertSettingsBinding

    private val jsonFileRegex: Regex by lazy {
        Regex("^[A-Za-z0-9+_.-]+.json")
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        customViewBinding = ViewAlertSettingsBinding.inflate(inflater, container, false)
        return customViewBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(customViewBinding) {
            alertConfirmButton.clickWithDebounce {
                val text = "${alertTextField.text}"
                checkIfValid(text)
            }
        }
    }

    private fun checkIfValid(text: String) {
        if (jsonFileRegex.matches(text)) {
            lifecycleScope.launch {
                preferenceRepository.save(PreferenceKey.ApiFileName, text)
                resultContinuation?.resume(AlertPopUpResult.YES)
            }
        } else {
            showErrorToast()
        }
    }

    private fun showErrorToast() {
        val errorText = resources.getString(R.string.settings_fail_message)
        customViewBinding.alertTextFieldContainer.error = errorText
        Toast.makeText(requireContext(), errorText, Toast.LENGTH_SHORT).show()
    }

    override fun onCancel(dialog: DialogInterface) {
        resultContinuation?.resume(AlertPopUpResult.QUIT)
        super.onCancel(dialog)
    }

    suspend fun getResult(): AlertPopUpResult = suspendCancellableCoroutine { resultContinuation = it }

    companion object {
        suspend fun create(
            fragmentManager: FragmentManager
        ): AlertPopUpResult = withContext(Dispatchers.Main) {
            val fragment = SettingsAlertPopUp()
            fragment.show(fragmentManager, "SettingsAlertPopUp")
            val result = fragment.getResult()
            fragment.dismiss()
            return@withContext result
        }
    }
}
