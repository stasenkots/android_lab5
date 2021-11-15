package com.stasenkots.android_lab4.form

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import com.stasenkots.android_lab4.CancelDialogActivity
import com.stasenkots.android_lab4.Field
import com.stasenkots.android_lab4.R
import com.stasenkots.android_lab4.databinding.ActivityAddressFormBinding

class AddressFormActivity: CancelDialogActivity() {

    private lateinit var binding: ActivityAddressFormBinding

    override val menuId: Int = R.menu.form_menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val country = intent.getStringExtra(Field.country).orEmpty()
        val city = intent.getStringExtra(Field.city).orEmpty()
        val address = intent.getStringExtra(Field.address).orEmpty()

        binding.country.setText(country)
        binding.country.doOnTextChanged { _, _, _, _ ->
            binding.countryLayout.error = null
        }
        binding.city.setText(city)
        binding.city.doOnTextChanged { _, _, _, _ ->
            binding.cityLayout.error = null
        }
        binding.address.setText(address)
        binding.address.doOnTextChanged { _, _, _, _ ->
            binding.addressLayout.error = null
        }

        setupSave()
        setupCancel()
    }

    private fun setupCancel() {
        binding.cancel.setOnClickListener {
            setResult(RESULT_CANCELED, null)
            finish()
        }
    }

    private fun setupSave() {
        binding.save.setOnClickListener {
            if (!validFields()) return@setOnClickListener

            val country = binding.country.text.toString()
            val city = binding.city.text.toString()
            val address = binding.address.text.toString()
            val returnIntent = Intent().apply {
                putExtras(
                    bundleOf(
                        Field.country to country,
                        Field.city to city,
                        Field.address to address
                    )
                )
            }
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }

    private fun validFields(): Boolean {
        return when {
            binding.country.text.isNullOrBlank() -> {
                binding.countryLayout.error = getString(R.string.not_empty)
                false
            }
            binding.city.text.isNullOrBlank() -> {
                binding.cityLayout.error = getString(R.string.not_empty)
                false
            }
            binding.address.text.isNullOrBlank() -> {
                binding.addressLayout.error = getString(R.string.not_empty)
                false
            }
            else -> true

        }
    }
}