package com.allaboutkorea.allaboutkorea.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.allaboutkorea.allaboutkorea.MainActivity
import com.allaboutkorea.allaboutkorea.R
import com.allaboutkorea.allaboutkorea.databinding.ActivityLoginactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class loginactivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginactivityBinding

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginactivity)

        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this,R.layout.activity_loginactivity)

        //12.로그인 버튼을 누르면 어떻게 되는지에 대한 것...
        //로그인 로직을 만들기 위해서는 다음과 같은 코드를 잘 복붙하도록 하자.

    binding.loginbutton.setOnClickListener {

        val email = binding.emailarea.text.toString()
        val password = binding.passwordarea.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    //성공한다면

                    //똑같이, 이후에 다른 화면으로 이동시킨다.
                    val intent = Intent(this, MainActivity::class.java)
                    //메인 엑티비티에 간 상태에서 1번 더 뒤로가기를 누르면 완전히 종료되도록 한다. 이건 구현되는데 시간이 걸린다.
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)

                    Toast.makeText(this,"login successful.",Toast.LENGTH_LONG).show()

                } else {

                    Toast.makeText(this,"login failed.",Toast.LENGTH_LONG).show()

                    //실패한다면
                 }
            }



    }
    }
}