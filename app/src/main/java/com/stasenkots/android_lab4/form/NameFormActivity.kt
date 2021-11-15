package com.stasenkots.android_lab4.form

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import com.stasenkots.android_lab4.BundleKey
import com.stasenkots.android_lab4.CancelDialogActivity
import com.stasenkots.android_lab4.Field
import com.stasenkots.android_lab4.R
import com.stasenkots.android_lab4.databinding.ActivityNameFormBinding
import com.stasenkots.android_lab4.dialogs.CancelDialogFragment
import com.stasenkots.android_lab4.dialogs.CancelDialogResult

class NameFormActivity: CancelDialogActivity() {

    private lateinit var binding: ActivityNameFormBinding

    override val menuId: Int = R.menu.form_menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(Field.name).orEmpty()
        val surname = intent.getStringExtra(Field.surname).orEmpty()

        binding.name.setText(name)
        binding.name.doOnTextChanged { _, _, _, _ ->
            binding.nameLayout.error = null
        }
        binding.surname.setText(surname)
        binding.surname.doOnTextChanged { _, _, _, _ ->
            binding.surnameLayout.error = null
        }
        setupSave()
        setupCancel()
    }

    private fun setupCancel() {
        binding.cancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, null)
            finish()
        }
    }

    private fun setupSave() {
        binding.save.setOnClickListener {
            if (!validFields()) return@setOnClickListener

            val surname = binding.surname.text.toString()
            val returnIntent = Intent().apply {
            val name = binding.name.text.toString()
                putExtras(
                    bundleOf(
                        Field.name to name,
                        Field.surname to surname
                    )
                )
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    private fun validFields(): Boolean {
        return when {
            binding.name.text.isNullOrBlank() -> {
                binding.nameLayout.error = getString(R.string.not_empty)
                false
            }
            binding.surname.text.isNullOrBlank() -> {
                binding.surnameLayout.error = getString(R.string.not_empty)
                false
            }
            else -> true

        }
    }



}