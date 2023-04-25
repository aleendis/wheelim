package com.example.wheelim

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.wheelim.databinding.ActivityMainBinding

class login : AppCompatActivity{

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this, JoinActivity::class.java)
        binding.button.signup.setOnClickListener{startActivity(intent)}
    }
}