package com.stasenkots.android_lab4

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.stasenkots.android_lab4.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var name = "Tanya"
    private var surname = "Stasenko"
    private var country = "Belarus"
    private var city = "Minsk"
    private var address = "Nezaleshnasi 4"
    private var comment = "No comment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupEditName()
        setupEditAddress()
        setupEditComment()
        setupDataViews()

    }

    private fun setupEditName() {
        setupEditButton(
            binding.editName,
            NameFormActivity::class.java,
            ::getNameArgs,
            ::renderResultName
        )
    }

    private fun setupEditAddress() {
        setupEditButton(
            binding.editAddress,
            AddressFormActivity::class.java,
            ::getAddressArgs,
            ::renderResultAddress
        )
    }


    private fun setupEditComment() {
        setupEditButton(
            binding.editComment,
            CommentFormActivity::class.java,
            ::getCommentArgs,
            ::renderResultComment
        )
    }

    private fun setupEditButton(
        button: ImageButton,
        formClass: Class<out Activity>,
        getArgs: () -> Bundle,
        renderResult: (Intent) -> Unit
    ) {
        val launcher = getActivityLauncher(renderResult)

        button.setOnClickListener {
            launchActivity(
                formClass,
                getArgs,
                launcher
            )
        }
    }


    private fun launchActivity(
        activityClass: Class<out Activity>,
        getArgs: () -> Bundle,
        form: ActivityResultLauncher<Intent>
    ) {
        val intent = Intent(this, activityClass).apply {
            putExtras(getArgs())
        }
        form.launch(intent)
    }

    private fun getActivityLauncher(renderOk: (Intent) -> Unit): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    val intent = result.data ?: return@registerForActivityResult
                    renderOk(intent)
                }
                RESULT_CANCELED -> return@registerForActivityResult
            }
        }
    }


    private fun renderResultName(intent: Intent) {
        name = intent.getStringExtra(Constants.name).orEmpty()
        surname = intent.getStringExtra(Constants.surname).orEmpty()
        setupDataViews()
    }

    private fun renderResultAddress(intent: Intent) {
        country = intent.getStringExtra(Constants.country).orEmpty()
        city = intent.getStringExtra(Constants.city).orEmpty()
        address = intent.getStringExtra(Constants.address).orEmpty()
        setupDataViews()
    }

    private fun renderResultComment(intent: Intent) {
        comment = intent.getStringExtra(Constants.comment).orEmpty()
        setupDataViews()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(
            bundleOf(
                Constants.name to name,
                Constants.surname to surname,
                Constants.city to city,
                Constants.country to country,
                Constants.address to address,
                Constants.comment to comment,
            )
        )
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        name = savedInstanceState.getString(Constants.name, "").orEmpty()
        surname = savedInstanceState.getString(Constants.surname, "").orEmpty()
        city = savedInstanceState.getString(Constants.city, "").orEmpty()
        country = savedInstanceState.getString(Constants.country, "").orEmpty()
        address = savedInstanceState.getString(Constants.address, "").orEmpty()
        comment = savedInstanceState.getString(Constants.comment, "").orEmpty()
        setupDataViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupDataViews() {
        binding.name.setText("$name $surname")
        binding.address.setText("$country, $city, $address")
        binding.comment.setText(comment)

    }



    private fun getNameArgs(): Bundle {
        return bundleOf(
            Constants.name to name,
            Constants.surname to surname
        )
    }

    private fun getAddressArgs(): Bundle {
        return bundleOf(
            Constants.country to country,
            Constants.city to city,
            Constants.address to address
        )
    }

    private fun getCommentArgs(): Bundle {
        return bundleOf(Constants.comment to comment)
    }

}