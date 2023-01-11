package com.AllAboutKorea.allaboutkorea.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.AllAboutKorea.allaboutkorea.R
import com.AllAboutKorea.allaboutkorea.databinding.ActivityBroadwriteactivityBinding
import com.AllAboutKorea.allaboutkorea.utils.FBAuth
import com.AllAboutKorea.allaboutkorea.utils.FBRef
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class BoardWriteActivity : AppCompatActivity() {


    //28.보드에 write를 하면 동작하는 코드.
    //아직 미숙한 부분이기 때문에, 잘 보도록 하자.
    private lateinit var binding : ActivityBroadwriteactivityBinding


    private val TAG = BoardWriteActivity::class.java.simpleName

    //29.이미지 업로드에 대한 boolean 타입의 변수를 선언한다.
    private var isImageUpload = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //activity_broadwriteactivity에 대한 정보를 받아온다.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_broadwriteactivity)



        binding.writeBtn.setOnClickListener {


            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()

            Log.d(TAG, title)
            Log.d(TAG, content)

            // 파이어베이스 store에 이미지를 저장하고 싶습니다
            // 만약에 내가 게시글을 클릭했을 때, 게시글에 대한 정보를 받아와야 하는데
            // 이미지 이름에 대한 정보를 모르기 때문에
            // 이미지 이름을 문서의 key값으로 해줘서 이미지에 대한 정보를 찾기 쉽게 해놓음.


            val key = FBRef.boardRef.push().key.toString()


            FBRef.boardRef
                .child(key)
                .setValue(BoardModel(title, content, uid, time))

            Toast.makeText(this, "content added successful.", Toast.LENGTH_SHORT).show()

            if(isImageUpload == true) {
                imageUpload(key)
            }

            finish()


        }

        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
            isImageUpload = true
        }

    }

    private fun imageUpload(key : String){
        // Get the data from an ImageView as bytes

        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key + ".png")

        val imageView = binding.imageArea
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }




    //데이터를 받아 오는 데에 조건을 확인하는 코드들.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100) {
        //이 코드는 조건을 만족했을 시 이미지를 추가하는 코드이다.
            binding.imageArea.setImageURI(data?.data)

        }

    }
}