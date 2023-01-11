package com.allaboutkorea.allaboutkorea

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.allaboutkorea.allaboutkorea.auth.introactivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        auth = Firebase.auth


        val logoutBtn = findViewById<Button>(R.id.logoutbutton)
        //val logoutBtn : Button = findViewById(R.id.logoutbutton)

       //로그아웃 기능 구현. 그냥 signOut만 누르면 된다.
        logoutBtn.setOnClickListener {
            auth.signOut()
            Toast.makeText(this,"logout successful.",Toast.LENGTH_SHORT).show()

            //로그아웃을 누르고 나서, introActivity로 옮겨준다.
            val intent = Intent(this,introactivity::class.java)
            //로그아웃을 누르고, 뒤로가기 버튼을 1번 누르면 엉뚱한 페이지로 이동하는 것을 막기위한 코드이다.
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
    }
}