package com.allaboutkorea.allaboutkorea.utils

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*


//33.fbauth는 파이어베이스에 저장되어 있는 것들, 특히 auth에 관한 것을 불러 오는 코드이다.
// uid를 받아오는 함수인 getuid를 새로 만들었다.
//해당 게시글 등의 uid를 받아오는게 getuid이다.
//return 에서 현재 유저의 uid를 받아 왔다는 것을 알 수 있다.

class FBAuth {
    companion object{
        private lateinit var auth : FirebaseAuth

        fun getUid() : String{
            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.uid.toString()
        }

        fun getTime() : String{
            //시간을 가져오는 코드를 작성한다.
            val currentDataTime = Calendar.getInstance().time
            val dataformat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss",Locale.KOREA).format(currentDataTime)

            return dataformat
        }
    }
}