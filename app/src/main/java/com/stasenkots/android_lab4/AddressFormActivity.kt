package com.stasenkots.android_lab4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import com.stasenkots.android_lab4.databinding.ActivityAddressFormBinding
import com.stasenkots.android_lab4.databinding.ActivityNameFormBinding

class AddressFormActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddressFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val country = intent.getStringExtra(Constants.country).orEmpty()
        val city = intent.getStringExtra(Constants.city).orEmpty()
        val address = intent.getStringExtra(Constants.address).orEmpty()

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
                        Constants.country to country,
                        Constants.city to city,
                        Constants.address to address
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