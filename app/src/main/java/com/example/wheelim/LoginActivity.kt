package com.example.wheelim

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.wheelim.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity{

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    private lateinit var blinding:ActivityMainBinding
    var toggle: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)

        val view = binding.root
        setContentView(R.layout.activity_login)

        binding.login_botton.setOnClickListener{
            if(toggle) binding.textView
        }
        login_button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}