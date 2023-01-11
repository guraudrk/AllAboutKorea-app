package com.allaboutkorea.allaboutkorea.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



//34.파이어베이스에 관한 것들,특히 getreference에 관한 것을 불러 오는 코드이다.
class FBRef {


    //일일이 데이터베이스를 선언하는 과정을 줄이기 위한 코드이다.

    companion object{

        private val database = Firebase.database


        //카테고리에 관한 레퍼런스를 그때그때 불러오는 것도 싫으니, 이렇게 따로 정의를 해 두는 것도
        //나쁘지 않을 것이다.

        val category_history = database.getReference("history")
        val category_food = database.getReference("food")
        val category_k_pop = database.getReference("k-pop")
        val category_k_drama = database.getReference("k-drama")
        val category_korean = database.getReference("korean")
        val category_travel = database.getReference("travel")
        val category_funfacts = database.getReference("funfacts")
        val category_economy = database.getReference("economy")
        val category_k_beauty = database.getReference("k_beauty")

        val bookmarkref = database.getReference("bookmark_list")

        //게시판에 대한 레퍼런스를 저장하는 것이다.
        val boardRef = database.getReference("board")


        //댓글에 대한 레퍼런스를 저장하는 것이다.
        val commentRef = database.getReference("comment")
        val gallery = database.getReference("gallery")


    }
}