package com.example.wheelim

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class JoinActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        val btnclose = findViewById<Button>(R.id.delete)
        val btnconfirm = findViewById<Button>(R.id.join_button)
        val btnsend = findViewById<Button>(R.id.check_button)

        btnclose.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnconfirm.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        /*btnsend.setOnClickListener {
            val intent = Intent(this, )
        }*/

    }


}