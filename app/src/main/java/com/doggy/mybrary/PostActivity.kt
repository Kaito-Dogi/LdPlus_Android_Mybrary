package com.doggy.mybrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        var pageText: Int = 0
        var sentence1: String = ""
        var sentence2: String = ""
        var sentence3: String = ""

        postButton.setOnClickListener {
            toMainActivity()
        }
    }

    fun toMainActivity(){
        //Intentのインスタンスを作成
        val intent = Intent(this, MainActivity::class.java)
        //画面遷移を開始
        startActivity(intent)
    }
}

