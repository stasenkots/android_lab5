package com.stasenkots.android_lab4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.stasenkots.android_lab4.databinding.ActivityAddressFormBinding
import com.stasenkots.android_lab4.databinding.ActivityCommentFormBinding

class CommentFormActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCommentFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val comment = intent.getStringExtra(Constants.comment).orEmpty()

        binding.comment.setText(comment)

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
            val comment = binding.comment.text.toString()

            val returnIntent = Intent().apply {
                putExtras(
                    bundleOf(
                        Constants.comment to comment,
                    )
                )
            }
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }

}