package com.doggy.mybrary

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val dataStore: SharedPreferences = getSharedPreferences("DataStore", Context.MODE_PRIVATE)

        createButton.setOnClickListener {
            if (totalPageTextView.text.toString() != ""){
                val editor = dataStore.edit()
                editor.putString("Title", titleTextView.text.toString())
                editor.apply()
                editor.putString("Page", totalPageTextView.text.toString())
                editor.apply()

                deleteAll()

                toMainActivity()
                Toast.makeText(this@StartActivity, "Welcome to NEW Mybrary.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@StartActivity, "How many pages does your book have?", Toast.LENGTH_LONG).show()
            }
        }

        continueButton.setOnClickListener {
            toMainActivity()
            Toast.makeText(this@StartActivity, "Welcome back, owner.", Toast.LENGTH_SHORT).show()
        }

    }

    fun toMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun deleteAll() {
        realm.executeTransaction {
            realm.deleteAll()
        }
    }
}
