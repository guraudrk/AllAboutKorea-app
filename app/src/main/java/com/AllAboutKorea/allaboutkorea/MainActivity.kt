package com.AllAboutKorea.allaboutkorea

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class MainActivity : AppCompatActivity() {

    //뒤로 가기 2번을 누르면 종료시켜주는 놈이다.
    private var isDouble = false
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting 부분을 누르면 '설정'으로 이동하는 부분을 구현한다.
        findViewById<ImageView>(R.id.settingBtn).setOnClickListener {
            val intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)

        }

        //로그아웃을 구현하는 부분이다.
        // findViewById<Button>(R.id.logoutbtn).setOnClickListener {

        //  auth.signOut()
        //이렇게 하고 로그아웃을 한다.
        //그런데, 로그아웃을 하고 화면을 전환시켜주면 더 좋을 것이다.

        //   val intent = Intent(this, introactivity::class.java)
        //  intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        // startActivity(intent)


    }

    override fun onBackPressed() {
        if(isDouble==true)
        {
            finish()
        }

        isDouble = true
        Toast.makeText(this,"Press back button once more If you want to finish the app.",Toast.LENGTH_SHORT).show()
        Handler().postDelayed({
            isDouble=false
        },1500)
    }



}
