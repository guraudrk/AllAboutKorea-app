package com.AllAboutKorea.allaboutkorea

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.AllAboutKorea.allaboutkorea.contentslist.contentmodel
import com.AllAboutKorea.allaboutkorea.contentslist.contentrvadapter
import com.AllAboutKorea.allaboutkorea.utils.FBAuth
import com.AllAboutKorea.allaboutkorea.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class KdramaActivity : AppCompatActivity() {


    //31.database에 관한 것을 불러올 때 databasereference를 불러온다. 이거는 외워야 한다.
    lateinit var myRef : DatabaseReference


    //어뎁터를 불러오는 것은 똑같다.
    lateinit var rvadapter : contentrvadapter

    //북마크의 id들을 가져오는 함수를 하나 만든다.
    val bookmarkidlist = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kdrama)
        val rv : RecyclerView = findViewById(R.id.rv)
        val database = Firebase.database

        val items = ArrayList<contentmodel>()
        //데이터베이스의 키값을 받아오는 코드이다.
        val itemKeyList = ArrayList<String>()
        rvadapter =  contentrvadapter(baseContext,items,itemKeyList,bookmarkidlist)


        // Write a message to the database
        //파이어베이스의 database에 데이터를 저장해 두는 역활을 한다.


        //각자 다른 카테고리를 받았을 때에 대비한 코드.
        val category = intent.getStringExtra("category")







        //historyactivity이므로 이 코드를 작성한다.
        myRef = database.getReference("kdrama")











//여기는 데이터베이스에서 아이템을 가져와서 어뎁터에 동기화를 시키는 부분이다.

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //데이터를 더하는 불러오는 역활.
                for(dataModel in dataSnapshot.children){

                    //데이버베이스의 키값을 불러오는 코드이다.
                    Log.d("contentlistactivity",dataModel.key.toString())
                    val item =  dataModel.getValue(contentmodel::class.java)
                    items.add(item!!)
                    //for문을 통해 데이터베이스에 있는 키값을 저장한다.
                    itemKeyList.add(dataModel.key.toString())

                }
                //이걸 넣어야 데이터가 잘 나타난다.
                //간단히 이야기해서, 데이터가 받아와지는 와중에 앱이 실행되서
                //데이터가 안보일 수도 있는데, 이 코드는 그런 현상을 막는다.
                rvadapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.addValueEventListener(postListener)







//        items.add(contentmodel("title1","https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg/270px-FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg.png","https://en.wikipedia.org/wiki/FC_Bayern_Munich"))
//        items.add(contentmodel("title2","https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg/270px-FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg.png","https://en.wikipedia.org/wiki/FC_Bayern_Munich"))
//        items.add(contentmodel("title3","https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg/270px-FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg.png","https://en.wikipedia.org/wiki/FC_Bayern_Munich"))
//        items.add(contentmodel("title4","https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg/270px-FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg.png","https://en.wikipedia.org/wiki/FC_Bayern_Munich"))
//        items.add(contentmodel("title5","https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg/270px-FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg.png","https://en.wikipedia.org/wiki/FC_Bayern_Munich"))
//        items.add(contentmodel("title6","https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg/270px-FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg.png","https://en.wikipedia.org/wiki/FC_Bayern_Munich"))
//        items.add(contentmodel("title7","https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg/270px-FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg.png","https://en.wikipedia.org/wiki/FC_Bayern_Munich"))
//        items.add(contentmodel("title8","https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg/270px-FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg.png","https://en.wikipedia.org/wiki/FC_Bayern_Munich"))
//        items.add(contentmodel("title9","https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg/270px-FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg.png","https://en.wikipedia.org/wiki/FC_Bayern_Munich"))

        rv.adapter = rvadapter

        rv.layoutManager = GridLayoutManager(this,2)

        getbookmarkdata()



//        //클릭을 하면 showactivity로 이동하는 기능 구현
//        rvadapter.itemClick = object : contentrvadapter.ItemClick{
//            override fun OnClick(view: View, position: Int) {
//
//                Toast.makeText(baseContext,items[position].title,Toast.LENGTH_LONG).show()
//
//
//
//                val intent = Intent(this@contentlistactivity,contentshowadtivity::class.java)
//                //실제로 데이터를 showactivity 같은 곳에 넘겨주는 코드이다.
//                intent.putExtra("url",items[position].webUrl)
//
//                startActivity(intent)


        val history1 = database.getReference("history")

//        myRef.push().setValue(
//            contentmodel( "History of Korean television drama-Wikipedia",
//                "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/Jumong_site_1.jpg/375px-Jumong_site_1.jpg",
//                "https://en.wikipedia.org/wiki/Korean_drama"
//            )
//        )
//        myRef.push().setValue(
//            contentmodel( "27 Best Korean Drama Series You Can Stream on Netflix",
//                "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/korean-drama-series-1654114746.png?crop=1.00xw:1.00xh;0,0&resize=640:*",
//                "https://www.oprahdaily.com/entertainment/tv-movies/g30852507/best-korean-drama-series/"
//            )
//        )
//        myRef.push().setValue(
//            contentmodel( "Top 10 Websites to Watch Korean Dramas ",
//                "https://www.topikguide.com/wp-content/uploads/2022/05/TG-website-2022-05-13T173610.605-1-1024x493.jpg",
//                "https://www.topikguide.com/websites-to-watch-korean-dramas/"
//            )
//        )
//        myRef.push().setValue(
//            contentmodel( "18 Best KDrama Sites: Watch Korean Drama For Free [English Subs] 2022",
//                "https://www.techworm.net/wp-content/uploads/2018/12/Websites-to-download-Korean-Dramas-For-Free-782x420.jpg",
//                "https://www.techworm.net/2018/12/best-websites-download-korean-dramas-free.html"
//            )
//        )
//        myRef.push().setValue(
//            contentmodel( "Korea drama playlist on youtube!",
//                "https://assets.materialup.com/uploads/a5b1dfcc-135f-42fb-a166-aa04e752ccff/attachment.jpg",
//                "https://www.youtube.com/results?search_query=korea+drama+ost"
//            )
//        )
//        myRef.push().setValue(
//            contentmodel( "Kdrama – The Complete Guide to Korean Dramas",
//                "https://www.90daykorean.com/wp-content/uploads/2017/10/90-Day-Korean-Logo.png",
//                "https://www.90daykorean.com/kdrama/"
//            )
//        )


    }

    //32.북마크부분을 클릭할 때 작동하는 함수이다.
    //이놈도 쉽지 않다.
    private fun getbookmarkdata(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //북마크를 동적으로 삭제하기 위한 코드.
                //북마크 부분을 불러올 때 마다 데이터의 갱신이 필요하기 때문이다.
                bookmarkidlist.clear()


                //데이터를 더하는/불러오는 역활.
                for(dataModel in dataSnapshot.children){

                    //리스트 변수에 북마크에 관한 아이디들이 저장이 되었다.
                    bookmarkidlist.add(dataModel.key.toString())

                    //북마크에 관한 데이터를 받아올 때 오류를 줄여주는 코드
                    rvadapter.notifyDataSetChanged()
                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        //북마크 데이터를 불러오는 코드이다.
        FBRef.bookmarkref.child(FBAuth.getUid()).addValueEventListener(postListener)


    }




}