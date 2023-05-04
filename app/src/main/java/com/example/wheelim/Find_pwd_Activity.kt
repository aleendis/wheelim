package com.example.wheelim

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.wheelim.databinding.ActivityFindPwdBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth
class Find_pwd_Activity: AppCompatActivity() {
    private lateinit var binding: ActivityFindPwdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_pwd)

        binding.check.setOnClickListener{
            var isGoToJoin = true
            val email = binding.email.text.toString()
            val enroll = binding.num.text.toString()
            val intent = Intent(this, View_pwd_Activity::class.java)

            if(email.isEmpty()){
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if(enroll.isEmpty()){
                Toast.makeText(this, "인증번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            if(isGoToJoin){
                startActivity(intent)
            }
        }
        binding.cancel.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}