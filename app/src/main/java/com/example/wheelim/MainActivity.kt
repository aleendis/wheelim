package com.example.wheelim

import android.content.Intent
import android.location.LocationManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.wheelim.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var backPressedTime: Long = 0
    private lateinit var locationManager: LocationManager
    private lateinit var locationTextView: TextView

    private var firebaseDatabase: FirebaseDatabase? = null
    private var dbRef: DatabaseReference? = null

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

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbRef = firebaseDatabase?.getReference("IMU_LSM6DS3")
        readData()

        locationTextView = findViewById(R.id.gps_location)

        binding.call.setOnClickListener {
            isAlarmPlaying = false
            dbRef?.child("4-FallStatus")?.setValue(false)
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:119")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        binding.gpsSend.setOnClickListener {
            val latitude = "37.301670"
            val longitude = "127.923500"

            val phoneNumber = "119"

            val smsUri = Uri.parse("smsto: $phoneNumber")
            val smsIntent = Intent(Intent.ACTION_SENDTO, smsUri)
            smsIntent.putExtra("sms_body", "낙상사고가 발생한 좌표입니다!\n위도: $latitude\n경도: $longitude")

            if(smsIntent.resolveActivity(packageManager) != null){
                startActivity(smsIntent)
            }else{
                Toast.makeText(this, "문자 메시지 앱을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.logout.setOnClickListener {
            Firebase.auth.signOut()
            finishAffinity()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.mapView.setOnClickListener{
            startActivity(Intent(this, MapActivity::class.java))
        }

        binding.mute.setOnClickListener{
            dbRef?.child("4-FallStatus")?.setValue(false)
        }
    }
    private var isAlarmPlaying = false
    private fun readData(){
        dbRef?.child("4-FallStatus")?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(Boolean::class.java)
                val latitude = "37.301670"
                val longitude = "127.923500"
                if(value == true){
                    if(!isAlarmPlaying) {
                            playNotificationSound()
                            isAlarmPlaying = true
                    }
                    locationTextView.text = "위도: $latitude\n경도: $longitude"
                } else {
                    stopNotificationSound()
                    isAlarmPlaying = false
                    locationTextView.text = "위도: \n경도: "
                }
                Log.e("0000", "onDataChange: ${snapshot.child("4-FallStatus")}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("0000", "onCancelld: ${error.toException()}")
            }
        })
    }
    private var mediaPlayer: MediaPlayer? = null
    private fun playNotificationSound() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.alarm)
            mediaPlayer?.isLooping = true
        }
        mediaPlayer?.start()
    }
    private fun stopNotificationSound() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}