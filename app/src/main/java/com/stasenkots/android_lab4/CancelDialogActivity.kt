package com.stasenkots.android_lab4

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.stasenkots.android_lab4.BundleKey
import com.stasenkots.android_lab4.R
import com.stasenkots.android_lab4.dialogs.CancelDialogFragment
import com.stasenkots.android_lab4.dialogs.CancelDialogResult

private const val TAG_CANCEL_DIALOG = "Cancel dialog"

abstract class CancelDialogActivity: AppCompatActivity() {

    protected abstract val menuId: Int

    override fun onStart() {
        super.onStart()
        setCancelDialogFragmentResultListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(menuId, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cancel -> showCancelDialog()
        }
        return true
    }

    private fun setCancelDialogFragmentResultListener() {
        supportFragmentManager.setFragmentResultListener(
            CancelDialogFragment.requestKey,
            this
        ) { _, result ->
            val answer = result.get(BundleKey.answer) as CancelDialogResult
            when (answer) {
                CancelDialogResult.YES -> renderYesAnswer()
                CancelDialogResult.NO -> return@setFragmentResultListener
            }
        }
    }

    private fun showCancelDialog() {
        CancelDialogFragment().show(supportFragmentManager, TAG_CANCEL_DIALOG)
    }

    private fun renderYesAnswer() {
        finish()
    }
}