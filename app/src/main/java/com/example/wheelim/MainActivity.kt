package com.example.wheelim

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.wheelim.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

private lateinit var auth: FirebaseAuth

class MainActivity : AppCompatActivity() {
    val mContext = this
    private lateinit var binding: ActivityMainBinding
    private var backPressedTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - backPressedTime >= 2000) {
            backPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.call.setOnClickListener() {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:119")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

    }
}