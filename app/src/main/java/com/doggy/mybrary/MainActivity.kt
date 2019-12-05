package com.doggy.mybrary

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.titleTextView
import kotlinx.android.synthetic.main.content_main.totalPageTextView
import java.util.*

class MainActivity : AppCompatActivity() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val dataStore: SharedPreferences = getSharedPreferences("DataStore", Context.MODE_PRIVATE)
        titleTextView.text = dataStore.getString("Title", "")
        totalPageTextView.text = dataStore.getString("Page", "")

        val PostList = readAll()

        // ポストリストが空だったときにダミーデータを生成する
        if (PostList.isEmpty()) {
            createDummyData()
        } else {
        }

        val adapter =
            PostAdapter(this, PostList, object : PostAdapter.OnItemClickListener {
                override fun onItemClick(item: Post) {
                    // クリックした処理を書く
                    Toast.makeText(this@MainActivity, "It has been deleted.", Toast.LENGTH_SHORT)
                        .show()
                    delete(item)
                }
            }, true)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fab.setOnClickListener {
            toPostActivity()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun createDummyData() {
        create(0, "Welcome to Mybrary!", "Let's write freely your impressions, summary,", "or sometimes jokes!")
    }

    fun create(nowPage: Int, sentence1: String, sentence2: String, sentence3: String) {
        realm.executeTransaction {
            val post = it.createObject(Post::class.java, UUID.randomUUID().toString())
            post.nowPage = nowPage
            post.nowPersent = nowPage
            post.sentence1 = sentence1
            post.sentence2 = sentence2
            post.sentence3 = sentence3
        }
    }

    fun readAll(): RealmResults<Post> {
        return realm.where(Post::class.java).findAll().sort("createdAt", Sort.ASCENDING)
    }

    fun update(id: String, nowPage: Int, nowPersent: Int, sentence1: String, sentence2: String, sentence3: String) {
        realm.executeTransaction {
            val post = realm.where(Post::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            post.nowPage = nowPage
            post.nowPersent = nowPersent
            post.sentence1 = sentence1
            post.sentence2 = sentence2
            post.sentence3 = sentence3
        }
    }

    fun update(post: Post, nowPage: Int, sentence1: String, sentence2: String, sentence3: String) {
        realm.executeTransaction {
            post.nowPage = nowPage
            post.nowPersent = nowPage
            post.sentence1 = sentence1
            post.sentence2 = sentence2
            post.sentence3 = sentence3
        }
    }

    fun delete(id: String) {
        realm.executeTransaction {
            val post = realm.where(Post::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            post.deleteFromRealm()
        }
    }

    fun delete(post: Post) {
        realm.executeTransaction {
            post.deleteFromRealm()
        }
    }

    fun deleteAll() {
        realm.executeTransaction {
            realm.deleteAll()
        }
    }

    fun toPostActivity() {
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
    }

}

