package com.example.wheelim

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.wheelim.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth
class JoinActivity: AppCompatActivity() {
    private lateinit var binding: ActivityJoinBinding
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }
    private fun reload() {
        TODO("Not yet implemented")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        binding.joinButton.setOnClickListener{
            var isGoToJoin = true
            val email = binding.joinEmail.text.toString()
            val password = binding.joinPassword.text.toString()
            val passwordcheck = binding.joinPwck.text.toString()
            val intent = Intent(this, LoginActivity::class.java)

            if(email.isEmpty()){
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if(password.isEmpty()){
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if(passwordcheck.isEmpty()){
                Toast.makeText(this, "비밀번호 확인을 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if(password != passwordcheck){
                Toast.makeText(this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if(password.length < 6){
                Toast.makeText(this, "비밀번호를 6자리 이상 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if(isGoToJoin){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful){
                            Log.d(TAG, "유저정보 생성완료")
                            val user = auth.currentUser
                            updateUI(user)
                            Toast.makeText(this, "회원가입에 성공했습니다.", Toast.LENGTH_LONG).show()
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "이미 존재하는 계정입니다.", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        binding.delete.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        TODO("Not yet implemented")
    }
}