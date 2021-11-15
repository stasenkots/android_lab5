package com.stasenkots.android_lab4.form

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import com.stasenkots.android_lab4.CancelDialogActivity
import com.stasenkots.android_lab4.Field
import com.stasenkots.android_lab4.R
import com.stasenkots.android_lab4.databinding.ActivityCommentFormBinding

class CommentFormActivity: CancelDialogActivity() {

    private lateinit var binding: ActivityCommentFormBinding

    override val menuId: Int = R.menu.form_menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val comment = intent.getStringExtra(Field.comment).orEmpty()

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
                        Field.comment to comment,
                    )
                )
            }
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }

}