package com.example.wheelim

import android.content.ContentValues.TAG
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.nfc.Tag
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
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
    override fun onBackPressed() {
        //     super.onBackPressed()
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_pwd)



        binding.check.setOnClickListener{
            var isGoToJoin = true
            val email = binding.email.text.toString()
            val intent = Intent(this, LoginActivity::class.java)

            if(email.isEmpty()){
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }else{
                if(isGoToJoin)
                    Firebase.auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener{task ->
                            if(task.isSuccessful){
                                Log.d(TAG, "Email sent.")
                                Toast.makeText(this, "메일 전송에 성공했습니다.", Toast.LENGTH_SHORT).show()
                                finishAffinity()
                                startActivity(intent)
                            }
                            if(task.isCanceled){
                                Log.d(TAG, "Email sent failure")
                                Toast.makeText(this, "이메일을 확인해주세요.", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }
        binding.cancel.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }
    }
}