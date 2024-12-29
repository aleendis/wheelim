package com.example.wheelim

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.wheelim.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth
class JoinActivity: AppCompatActivity() {
    private lateinit var binding: ActivityJoinBinding

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        binding.joinButton.setOnClickListener{
            var isGoToJoin = true
            val email = binding.joinEmail.text.toString()
            val password = binding.joinPassword.text.toString()
            val passwordcheck = binding.joinPwck.text.toString()
            val intent = Intent(this, LoginActivity::class.java)

            if(email.isEmpty()){
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            if(password.isEmpty()){
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            if(passwordcheck.isEmpty()){
                Toast.makeText(this, "비밀번호 확인을 입력해주세요.", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            if(password != passwordcheck){
                Toast.makeText(this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            if(password.length < 6){
                Toast.makeText(this, "비밀번호를 6자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            if(isGoToJoin){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                            finishAffinity()
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener(this){ exception ->
                        Toast.makeText(this, "회원가입 도중 오류가 발생했습니다: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        binding.delete.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }
    }

}