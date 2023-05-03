package com.example.wheelim

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button

class Find_pwd_Activity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_pwd)

        val btnsend = findViewById<Button>(R.id.mail_send)
        val btncheck = findViewById<Button>(R.id.check)
        val btncancel = findViewById<Button>(R.id.cancel)

       /* btnsend.setOnClickListener {
            val intent = Intent(this, )
        }*/
        btncheck.setOnClickListener {
            val intent = Intent(this, View_pwd_Activity::class.java)
            startActivity(intent)
        }
        btncancel.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}