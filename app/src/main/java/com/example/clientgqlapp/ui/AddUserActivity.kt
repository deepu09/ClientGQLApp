package com.example.clientgqlapp.ui

import android.os.Bundle
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.clientgqlapp.R
import com.example.clientgqlapp.util.AplClient
import kotlinx.android.synthetic.main.content_add_user.*

class AddUserActivity : AppCompatActivity() {

    private var isShow: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        updateUI()

    }

    private fun showOrHideHobbyAndPostView(isShow: Boolean) {
        val visibility = when(isShow) {
            true -> View.VISIBLE
            else -> View.GONE
        }
        user_post.visibility = visibility
        user_hobby_title.visibility = visibility
        user_hobby_description.visibility = visibility
        user_save_hobby_post.visibility = visibility
    }

    private fun updateUI() {
        showOrHideHobbyAndPostView(isShow)
        //user_name.addTextChangedListener(object: TextChan)
        user_save.setOnClickListener {
            saveUser()
        }
    }

    private fun saveUser() {
        AplClient.executeAddUserMutation(user_name.text.toString(), Integer.parseInt(user_age.text.toString()), user_profession.text.toString(),"$${user_salary.text}")
    }
}