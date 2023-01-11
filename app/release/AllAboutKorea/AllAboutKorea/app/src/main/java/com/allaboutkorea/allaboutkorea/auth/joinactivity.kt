package com.allaboutkorea.allaboutkorea.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.allaboutkorea.allaboutkorea.MainActivity
import com.allaboutkorea.allaboutkorea.R
import com.allaboutkorea.allaboutkorea.databinding.ActivityJoinactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class joinactivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding :ActivityJoinactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this,R.layout.activity_joinactivity)

        binding.joining.setOnClickListener {


            var isGoToJoin = true


            //9.실제로 사용자가 적은 부분을 바인딩해서 가져오고, 그걸 변수로 저장하는 코드이다.
            val email = binding.email.text.toString()
            val password1 = binding.password.text.toString()
            val password2 = binding.passwordcheck1.text.toString()


            //저기 값이 비어있는지 확인하는 로직부터 짠다.


            //10.로그인 할 때의 로직을 구현하는 코드.
            //이메일 부분이 비어 있는지, 비밀번호와 비밀번호 확인란을 같게 적었는지 등등의 사실을 점검한다.

            //만약 이메일 부분이 비어있다면
            if(email.isEmpty()){
                Toast.makeText(this,"please write down the email.", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }


            //만약 비밀번호 부분이 비어있다면
            if(password1.isEmpty()){
                Toast.makeText(this,"please write down the password.(more than 8 letters.)", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }

            //만약 비밀번호 확인 부분이 비어있다면
            if(password2.isEmpty()){
                Toast.makeText(this,"please write down the password. (more than 8 letters.)", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            if(!password1.equals(password2))
            {
                Toast.makeText(this,"please write password and password check equally.", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            //비밀번호 길이의 확인
            if(password1.length<8)
            {
                Toast.makeText(this,"please write password more than 8 letters.", Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }

            //11.만약 모든 조건들을 다 통과했다면,
            //이제는 회원 가입을 시켜 줄 때이다.
            if(isGoToJoin)
            {
                auth.createUserWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(this,"sign-up completed.", Toast.LENGTH_SHORT).show()
                        //회원 가입이 성공적으로 되었다면, 이제 화면을 mainactivity로 보내자.
                            val intent = Intent(this,MainActivity::class.java)
                            //메인 엑티비티에 간 상태에서 1번 더 뒤로가기를 누르면 완전히 종료되도록 한다. 이건 구현되는데 시간이 걸린다.

                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)


                        } else {

                            Toast.makeText(this,"error:please reboot the app.", Toast.LENGTH_SHORT).show()


                        }
                    }

            }
        }




    }
}