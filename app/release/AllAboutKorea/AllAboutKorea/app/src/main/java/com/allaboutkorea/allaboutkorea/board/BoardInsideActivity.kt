package com.allaboutkorea.allaboutkorea.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.allaboutkorea.allaboutkorea.R
import com.allaboutkorea.allaboutkorea.comment.commentListViewAdapter
import com.allaboutkorea.allaboutkorea.comment.commentmodel
import com.allaboutkorea.allaboutkorea.databinding.ActivityBoardInsideBinding
import com.allaboutkorea.allaboutkorea.utils.FBAuth
import com.allaboutkorea.allaboutkorea.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import fragment.TalkFragment
import java.lang.Exception

class BoardInsideActivity : AppCompatActivity() {

    //어뎁터, 데이터 바인딩, 키값, 배열 등을 정의하는 부분이다.

    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var binding : ActivityBoardInsideBinding

    private lateinit var key:String

    //19.댓글 부분을 담는 함수 코드이다.

    private val commentDataList = mutableListOf<commentmodel>()


    //20.어뎁터 부분을 불러온다.
    //commentAdapter는 commentListViewAdapter를 불러온다는 의미이다.
    private lateinit var commentAdapter : commentListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        //'설정' 부분을 누르면 나타나는 코드.
        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }

        // 보드,이미지에 관한 키를 받아온다.
        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)


        //댓글을 쓰는 버튼을 누르면, 댓글이 추가되는 함수가 실행된다.
        binding.commentBtn.setOnClickListener {
            insertComment(key)
        }

        getCommentData(key)


        //21.댓글 리스트뷰를 불러오는 어뎁터 부분
        //처음에는 이해가 어려울 수 있으니 반복해서 봐야 한다.
        commentAdapter = commentListViewAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter

    }

    //22.댓글에 관한 데이터를 불러오는 부분이다.
    //자세한 부분은 함수 내부 참고.
    fun getCommentData(key : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //데이터를 불러올 때 마다,새로고침이 필요하기 때문에,
                //함수를 호출 할 때 마다 clear를 적는다.
                commentDataList.clear()

                //데이터를 받아오는 부분. 이 부분은 이해가 따로 필요하다.
                for (dataModel in dataSnapshot.children) {

                    //datamodel은 commentmodel에서 value를 가져온다는 의미이다.
                    val item = dataModel.getValue(commentmodel::class.java)
                    commentDataList.add(item!!)
                }

                //데이터틀 받았으면,어뎁터와 동기화를 시켜주는 부분이다.
                commentAdapter.notifyDataSetChanged()


            }


            //만약 동작이 취소 되었다면??
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        //addValueEventListener는 파이어베이스에서 데이터를 불러오는 놈이다.
        FBRef.commentRef.child(key).addValueEventListener(postListener)


    }

    //23.댓글을 삽입하는 함수이다.
    fun insertComment(key : String){
        // comment
        //   - BoardKey
        //        - CommentKey
        //            - CommentData
        //            - CommentData
        //            - CommentData
        FBRef.commentRef
            .child(key)
            .push()
            .setValue(
                commentmodel(
                    binding.commentArea.text.toString(),
                    FBAuth.getTime()
                )
            )

        Toast.makeText(this, "comment has been successfully added.", Toast.LENGTH_SHORT).show()
        //댓글을 달고, 새 댓글을 달게 하기 위해 영역을 클리어해 주는 코드이다.
        binding.commentArea.setText("")

    }

    //24.수정이나 삭제를 해 주기 위해 다이얼로그를 보여주는 함수.
    private fun showDialog(){

        //수정이나 삭제를 하게 해 주는 페이지.
        //코드가 전부 다 이해가 가지는 않으므로, 복붙을 적극 활용하도록 하자.
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("correction/delete")

        val alertDialog = mBuilder.show()

        //수정 버튼을 눌렀을 때 동작하는 것
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {

            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key",key)
            startActivity(intent)
        }

        //삭제 버튼을 눌렀을 때 동작하는 것
        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {

            //삭제하는 코드
            FBRef.boardRef.child(key).removeValue()
            Toast.makeText(this, "comment has been successfully deleted.", Toast.LENGTH_SHORT).show()

            //삭제를 하고, 게시판 부분으로 이동한다.
            val intent = Intent(this,TalkFragment::class.java)
            startActivity(intent)

        }



    }

    //25.이미지를 받아오는 함수이다.
    private fun getImageData(key : String){



        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if(task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

                //데이터가 없으면, 설정해 두었던 getImageArea 보이지 않게 한다.
            } else {

                binding.getImageArea.isVisible = false
            }
        })


    }


    //26.보드의 데이터를 보여주는 함수이다.
//조금 어려우니까 잘 보도록 하자.
    private fun getBoardData(key : String){

        //object는 대상이 단 1번만 선언되게 한다.
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {

                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)
                    Log.d(TAG, dataModel!!.title)

                    //'우리는 dataModel의 데이터 타입들을 참조해 올 거야!!'

                    //'titleArea는 dataModel의 title을 가져와서 보여줄거야!!'
                    binding.titleArea.text = dataModel!!.title
                    binding.textArea.text = dataModel!!.content
                    binding.timeArea.text = dataModel!!.time

                    //내 uid와 글쓴이의 uid를 가져온다.
                    val myUid = FBAuth.getUid()
                    val writerUid = dataModel.uid


                    //이 게시글이 내가 쓴 글이라면
                    if(myUid.equals(writerUid)){
                        Log.d(TAG, "내가 쓴 글")
                        binding.boardSettingIcon.isVisible = true
                    }
                    //이 게시글이 내가 쓴 글이 아니라면,설정 버튼이 보이지 않는다.
                    else {
                        Log.d(TAG, "내가 쓴 글 아님")
                    }

                } catch (e : Exception){

                    //catch부분은 데이터를 삭제하고 다시 보여줬을 때, 오류를 막기 위한 코드이다.
                    Log.d(TAG, "삭제완료")

                }



            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        //이 코드는 코드의 기능만 이해해도 무방한 것 같다.
        FBRef.boardRef.child(key).addValueEventListener(postListener)



    }

}