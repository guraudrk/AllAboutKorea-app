package com.allaboutkorea.allaboutkorea.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.allaboutkorea.allaboutkorea.R
import com.allaboutkorea.allaboutkorea.databinding.ActivityBoardEditBinding
import com.allaboutkorea.allaboutkorea.utils.FBAuth
import com.allaboutkorea.allaboutkorea.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BoardEditActivity : AppCompatActivity() {


    //먼저 키,바인딩,테그,writerUid등을 불러온다.

    private lateinit var key:String

    private lateinit var binding : ActivityBoardEditBinding

    //13.TAG를 불러오는 코드. 이는 Log에서 잘 쓰인다.
    private val TAG = BoardEditActivity::class.java.simpleName

    //글쓴이에 관한 정보이다.
    private lateinit var writerUid : String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        //데이터 바인딩을 정의한다.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_edit)


        binding.editImageArea.setOnClickListener {


        }

        //14.키값을 받아온다.
        //getStringExtra를 통해 키값을 받아온다.
        //toString도 잊지 말 것!!
        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)

        //수정 버튼을 누르면 동작하는 코드. 키를 받아서 동작한다.
        binding.editBtn.setOnClickListener {
            editBoardData(key)
        }


    }

    //15.'수정'버튼을 누르면, 동작하는 코드이다. 자세한 부분은 코드 참조.

    private fun editBoardData(key : String){


        //16.수정한 글에 대한 정보들을 설정하는 코드이다. 자세한 부분은 코드 참조.
        FBRef.boardRef
            .child(key)
            .setValue(
                BoardModel(binding.titleArea.text.toString(), //titlearea의 text들을 받아왔다.
                    binding.contentArea.text.toString(), //contentArea의 text를 받아왔다.
                    writerUid,
                    FBAuth.getTime())
            )

        //수정이 완료되면, 위와 같은 메시지를 띄운다.
        Toast.makeText(this, "correction complete.", Toast.LENGTH_LONG).show()

        //엑티비티를 종료하는 함수이다.
        finish()

    }

    //17.이미지 데이터를 받아오는 코드.
    //자세한 사항은 코드 참조.
    private fun getImageData(key : String){



        // Reference to an image file in Cloud Storage
        //클라우드 저장소에서 이미지 파일을 참조하는 것
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.editImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if(task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

            } else {
                //이 코드를 적용시키면, 이미지가 없는 상태의 게시글을 불러올 때 난처해진다.
                //Toast.makeText(this,"Fail to getting the image data.",Toast.LENGTH_LONG).show()


            }
        })

        //이미지 데이터를 클릭한다면 나타나는 코드
        //이미지 데이터를 클릭하면, 이미지를 수정하는 기능을 넣는다.




    }

    //18.보드의 데이터를 받아오는 코드.
    //
    private fun getBoardData(key : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataModel = dataSnapshot.getValue(BoardModel::class.java)
//                Log.d(TAG, dataModel.toString())
//                Log.d(TAG, dataModel!!.title)
//                Log.d(TAG, dataModel!!.time)

                //수정을 하는 거기 때문에 data를 생으로 받아와야 한다.
                //그러므로, 바인딩을 통해 데이터를 받는다.
                binding.titleArea.setText(dataModel?.title)
                binding.contentArea.setText(dataModel?.content)
                writerUid = dataModel!!.uid


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        FBRef.boardRef.child(key).addValueEventListener(postListener)


    }







}