package com.example.wheelim

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.wheelim.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth

class LoginActivity : AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding
    private var backPressedTime: Long = 0
    override fun onBackPressed() {
        if(System.currentTimeMillis() - backPressedTime >= 2000){
            backPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }else{
            finish()
        }
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.signup.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

        binding.findPwd.setOnClickListener {
            val intent = Intent(this, Find_pwd_Activity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener{
            val email = binding.loginId.text.toString()
            val password = binding.loginPwd.text.toString()
            val intent = Intent(this, MainActivity::class.java)

            if(email.isNotEmpty() && password.isNotEmpty())
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "로그인에 성공했습니다!", Toast.LENGTH_SHORT).show()
                            finishAffinity()
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }
}
