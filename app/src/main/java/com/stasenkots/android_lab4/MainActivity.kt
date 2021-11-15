package com.stasenkots.android_lab4

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.stasenkots.android_lab4.databinding.ActivityMainBinding
import com.stasenkots.android_lab4.form.AddressFormActivity
import com.stasenkots.android_lab4.form.CommentFormActivity
import com.stasenkots.android_lab4.form.NameFormActivity

class MainActivity: CancelDialogActivity() {

    private lateinit var binding: ActivityMainBinding

    override val menuId: Int = R.menu.main_menu

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
        name = intent.getStringExtra(Field.name).orEmpty()
        surname = intent.getStringExtra(Field.surname).orEmpty()
        setupDataViews()
    }

    private fun renderResultAddress(intent: Intent) {
        country = intent.getStringExtra(Field.country).orEmpty()
        city = intent.getStringExtra(Field.city).orEmpty()
        address = intent.getStringExtra(Field.address).orEmpty()
        setupDataViews()
    }

    private fun renderResultComment(intent: Intent) {
        comment = intent.getStringExtra(Field.comment).orEmpty()
        setupDataViews()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(
            bundleOf(
                Field.name to name,
                Field.surname to surname,
                Field.city to city,
                Field.country to country,
                Field.address to address,
                Field.comment to comment,
            )
        )
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        name = savedInstanceState.getString(Field.name, "").orEmpty()
        surname = savedInstanceState.getString(Field.surname, "").orEmpty()
        city = savedInstanceState.getString(Field.city, "").orEmpty()
        country = savedInstanceState.getString(Field.country, "").orEmpty()
        address = savedInstanceState.getString(Field.address, "").orEmpty()
        comment = savedInstanceState.getString(Field.comment, "").orEmpty()
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
            Field.name to name,
            Field.surname to surname
        )
    }

    private fun getAddressArgs(): Bundle {
        return bundleOf(
            Field.country to country,
            Field.city to city,
            Field.address to address
        )
    }

    private fun getCommentArgs(): Bundle {
        return bundleOf(Field.comment to comment)
    }

}