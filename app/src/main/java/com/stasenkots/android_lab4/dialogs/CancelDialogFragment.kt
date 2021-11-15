package com.stasenkots.android_lab4.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.stasenkots.android_lab4.BundleKey
import com.stasenkots.android_lab4.R


class CancelDialogFragment: DialogFragment() {

    companion object{
        const val requestKey = "CancelDialogFragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.do_you_want_to_cancel_form)
            .setPositiveButton(R.string.yes) { _, _ ->
                setResult(CancelDialogResult.YES)
            }
            .setNegativeButton(R.string.no) { _, _ ->
                setResult(CancelDialogResult.NO)
            }
            .create()
    }

    private fun setResult(answer: CancelDialogResult) {
        val result = bundleOf(BundleKey.answer to answer)
        parentFragmentManager.setFragmentResult(
            requestKey,
            result
        )
        dialog?.dismiss()
    }
}

enum class CancelDialogResult {
    YES,
    NO
}