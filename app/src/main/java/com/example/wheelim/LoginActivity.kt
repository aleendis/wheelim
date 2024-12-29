package com.example.wheelim

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.wheelim.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private lateinit var auth: FirebaseAuth

class LoginActivity : AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding
    private var backPressedTime: Long = 0
    private val TAG = this.javaClass.simpleName

    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth

    private var email: String = ""
    private var tokenId: String? = null
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
    override fun onStart(){
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            finishAffinity()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ActivityResultCallback { result ->
                Log.e(TAG, "resultCode : ${result.resultCode}")
                Log.e(TAG, "result : $result")
                if(result.resultCode == RESULT_OK){
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try{
                        task.getResult(ApiException::class.java)?.let{ account ->
                            tokenId = account.idToken
                            if(tokenId != null && tokenId != ""){
                                val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                                firebaseAuth.signInWithCredential(credential)
                                    .addOnCompleteListener {
                                        if(task.isSuccessful){
                                            Log.e(TAG, "signInWithCredential: success")
                                            val user = auth.currentUser
                                            finishAffinity()
                                            startActivity(Intent(this, MainActivity::class.java))
                                            updateUI(user)
                                        }else{
                                            Log.w(TAG, "signInWithCredential: failure", task.exception)
                                            updateUI(null)
                                        }
                                    }
                            }else {
                                Log.d(TAG, "No ID token!")
                        }
                        } ?: throw Exception()
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            })

        binding.run{
            googleLogin.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()
                    val googleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso)
                    val signInIntent: Intent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                }
            }
        }

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

            if(email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "로그인에 성공했습니다!", Toast.LENGTH_SHORT).show()
                            finishAffinity()
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
    private fun updateUI(user: FirebaseUser?){

    }
}
