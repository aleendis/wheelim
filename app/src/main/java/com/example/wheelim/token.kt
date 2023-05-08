/*package com.example.wheelim

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class token {
    private fun initFirebase(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(task.isSuccessful){
                tokenTextView.text = task.result
            }
        }
    }
    class MyFirebaseMessagingService : FirebaseMessagingService(){
        override fun onNewToken(token: String){
            super.onNewToken(token)
        }

        override fun onMessageReceived(message: RemoteMessage) {
            super.onMessageReceived(message)
        }
    }
}*/