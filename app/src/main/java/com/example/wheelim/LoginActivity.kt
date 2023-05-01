package com.example.wheelim

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnlogin = findViewById<Button>(R.id.login_button)
        val btnclick = findViewById<Button>(R.id.signup)
        val btnclick2 = findViewById<Button>(R.id.find_pwd)

        btnlogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnclick.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
        btnclick2.setOnClickListener {
            val intent = Intent(this, Find_pwd_Activity::class.java)
            startActivity(intent)
        }
    }
    
}
