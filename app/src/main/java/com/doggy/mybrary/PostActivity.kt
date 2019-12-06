package com.doggy.mybrary

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_post.*
import java.util.*

class PostActivity : AppCompatActivity() {

    private val realmConfig = RealmConfiguration.Builder()
        .deleteRealmIfMigrationNeeded()
        .build()

    private val realm: Realm by lazy {
        Realm.getInstance(realmConfig)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val dataStore: SharedPreferences = getSharedPreferences("DataStore", Context.MODE_PRIVATE)
        val totalPage: Int = dataStore.getString("Page", "").toString().toInt()

        postButton.setOnClickListener {
            if (pageTextView.text.toString() == ""){
                Toast.makeText(this@PostActivity, "How far have you read?", Toast.LENGTH_SHORT)
                    .show()
            } else{
                val pageText = pageTextView.text.toString().toInt()
                val sentence1 = sentence1TextView.text.toString()
                val sentence2 = sentence2TextView.text.toString()
                val sentence3 = sentence3TextView.text.toString()

                create(pageText, totalPage, sentence1, sentence2, sentence3)

                toMainActivity()
            }
        }
    }

    fun toMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun create(nowPage: Int, totalPage: Int, sentence1: String, sentence2: String, sentence3: String) {
        realm.executeTransaction {
            val post = it.createObject(Post::class.java, UUID.randomUUID().toString())
            post.nowPage = nowPage
            post.nowPersent = nowPage * 100 / totalPage
            post.sentence1 = sentence1
            post.sentence2 = sentence2
            post.sentence3 = sentence3
        }
    }
}

