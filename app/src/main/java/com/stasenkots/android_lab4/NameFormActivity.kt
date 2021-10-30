package com.stasenkots.android_lab4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import com.stasenkots.android_lab4.databinding.ActivityNameFormBinding

class NameFormActivity: AppCompatActivity() {

    private lateinit var binding: ActivityNameFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(Constants.name).orEmpty()
        val surname = intent.getStringExtra(Constants.surname).orEmpty()

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

            val name = binding.name.text.toString()
            val surname = binding.surname.text.toString()
            val returnIntent = Intent().apply {
                putExtras(
                    bundleOf(
                        Constants.name to name,
                        Constants.surname to surname
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