package com.example.radiocomment

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var commentsLayout: LinearLayout
    private lateinit var commentEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        commentsLayout = findViewById(R.id.commentsLayout)
        commentEditText = findViewById(R.id.commentEditText)

        val postCommentBtn: Button = findViewById(R.id.postCommentBtn)
        postCommentBtn.setOnClickListener {
            postComment()
        }
    }

    private fun addCommentView(comment: Comment) {
        val commentView = layoutInflater.inflate(R.layout.comment_layout, commentsLayout, false)
        val usernameTextView: TextView = commentView.findViewById(R.id.usernameTextView)
        val commentTextView: TextView = commentView.findViewById(R.id.commentTextView)
        val timeStampTextView: TextView = commentView.findViewById(R.id.timeStampTextView)
        val upvoteCountTextView: TextView = commentView.findViewById(R.id.upvoteCountTextView)
        val downvoteCountTextView: TextView = commentView.findViewById(R.id.downvoteCountTextView)
        val upvoteBtn: Button = commentView.findViewById(R.id.upvoteBtn)
        val downvoteBtn: Button = commentView.findViewById(R.id.downvoteBtn)

        usernameTextView.text = comment.username
        commentTextView.text = comment.commentText
        timeStampTextView.text = comment.timeStamp
        upvoteCountTextView.text = comment.likes.toString()
        downvoteCountTextView.text = comment.dislikes.toString()

        upvoteBtn.setOnClickListener {
            comment.likes++
            upvoteCountTextView.text = comment.likes.toString()
        }

        downvoteBtn.setOnClickListener {
            comment.dislikes++
            downvoteCountTextView.text = comment.dislikes.toString()
        }

        commentsLayout.addView(commentView)

        // 👍ボタンのクリックリスナーを設定
        upvoteBtn.setOnClickListener {
            if (upvoteBtn.isSelected) {
                comment.likes--
                upvoteCountTextView.text = comment.likes.toString()
                upvoteBtn.isSelected = false
            } else {
                comment.likes++
                upvoteCountTextView.text = comment.likes.toString()
                upvoteBtn.isSelected = true
                // 👎ボタンが押されていた場合、取り消す
                if (downvoteBtn.isSelected) {
                    comment.dislikes--
                    downvoteCountTextView.text = comment.dislikes.toString()
                    downvoteBtn.isSelected = false
                }
            }
        }

        // 👎ボタンのクリックリスナーを設定
        downvoteBtn.setOnClickListener {
            if (downvoteBtn.isSelected) {
                comment.dislikes--
                downvoteCountTextView.text = comment.dislikes.toString()
                downvoteBtn.isSelected = false
            } else {
                comment.dislikes++
                downvoteCountTextView.text = comment.dislikes.toString()
                downvoteBtn.isSelected = true
                // 👍ボタンが押されていた場合、取り消す
                if (upvoteBtn.isSelected) {
                    comment.likes--
                    upvoteCountTextView.text = comment.likes.toString()
                    upvoteBtn.isSelected = false
                }
            }
        }
    }

    private fun postComment() {
        val commentText = commentEditText.text.toString()
        if (TextUtils.isEmpty(commentText)) {
            // コメントが空の場合は処理を中断する
            return
        }

        val username = "ユーザー1"
        val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val comment = Comment(username, commentText, timeStamp)
        addCommentView(comment)

        commentEditText.text.clear()
    }
}

data class Comment(
    val username: String,
    val commentText: String,
    val timeStamp: String,
    var likes: Int = 0,
    var dislikes: Int = 0
)
