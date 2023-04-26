package com.example.wheelim

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.wheelim.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(){

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this, JoinActivity::class.java)
        binding.signup.setOnClickListener{startActivity(intent)}
    }

    /*val bundle = Bundle()
    bundle.putString(FirebaseAnalytics.Param.METHOD, method)
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle) */
}
