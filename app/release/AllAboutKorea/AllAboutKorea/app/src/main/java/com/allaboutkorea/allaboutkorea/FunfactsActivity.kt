package com.allaboutkorea.allaboutkorea

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allaboutkorea.allaboutkorea.contentslist.contentmodel
import com.allaboutkorea.allaboutkorea.contentslist.contentrvadapter
import com.allaboutkorea.allaboutkorea.utils.FBAuth
import com.allaboutkorea.allaboutkorea.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FunfactsActivity : AppCompatActivity() {


    //31.database에 관한 것을 불러올 때 databasereference를 불러온다. 이거는 외워야 한다.
    lateinit var myRef : DatabaseReference


    //어뎁터를 불러오는 것은 똑같다.
    lateinit var rvadapter : contentrvadapter

    //북마크의 id들을 가져오는 함수를 하나 만든다.
    val bookmarkidlist = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funfacts)
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
        myRef = database.getReference("funfacts")











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


        val history1 = database.getReference("funfacts")

//        myRef.push().setValue(
//            contentmodel(
//                "Faker's Net Worth(2022)",
//                "https://foreignpolicyi.org/wp-content/uploads/2018/12/Faker.jpg",
//                "https://streamerfacts.com/faker-net-worth/"
//            )
//        )
//        myRef.push().setValue(
//            contentmodel(
//                "88 Interesting Facts About South Korea",
//                "https://ling-app.com/wp-content/uploads/2022/03/1-1-2.jpg",
//                "https://ling-app.com/ko/facts-about-south-korea/"
//            )
//        )
//        myRef.push().setValue(
//            contentmodel(
//
//                "E-sports in korea",
//            "https://www.seoulz.com/wp-content/uploads/2020/01/gaming-768x432.jpg",
//                "https://www.seoulz.com/esports-in-korea-a-complete-breakdown-of-the-korean-gaming-culture/"
//            )
//        )
//        myRef.push().setValue(
//            contentmodel(
//                "Facts About South Korea – 30 Interesting Things to Learn",
//                "https://www.90daykorean.com/wp-content/uploads/2014/07/Interesting-Facts-about-South-Korea-1.png",
//                "https://www.90daykorean.com/interesting-facts-about-south-korea/"
//
//            )
//        )
//        myRef.push().setValue(
//            contentmodel(
//                "South Korea's Gender Wars:Trolls,Threats and Anger Online",
//                "https://i.ytimg.com/vi/gy4oFVuNPkg/maxresdefault.jpg"
//            ,"https://www.youtube.com/watch?v=gy4oFVuNPkg"
//            )
//        )
//        myRef.push().setValue(
//            contentmodel(
//                "15 STUNNING CULTURE FACTS ABOUT SOUTH KOREA",
//                "https://cdn-cipak.nitrocdn.com/pwvDKirvFjozvJSRiAFrxtGhrBfsgugn/assets/static/optimized/rev-53affb2/wp-content/uploads/2020/04/15-Stunning-Culture-Facts-about-South-Korea-1-1024x683.jpg",
//"https://asiaexchange.org/blogs/15-stunning-facts-about-south-korea/"            )
//        )
//
//        myRef.push().setValue(
//            contentmodel(
//                "Cha Bum-Kun: An Asian football poineer(youtube)",
//            "https://i.ytimg.com/vi/6hi2twcAij4/maxresdefault.jpg",
//                "https://www.youtube.com/watch?v=6hi2twcAij4"
//
//            )
//        )
//
//        myRef.push().setValue(
//            contentmodel(
//                "I survived North Korea(youtube)",
//                "https://i.ytimg.com/vi/Ziqq5gUXu8g/maxresdefault.jpg",
//                "https://www.youtube.com/watch?v=Ziqq5gUXu8g"
//
//            )
//        )
//        myRef.push().setValue(
//            contentmodel(
//                "Evolution Of Evil E06: The Kim Dynasty of North Korea | Full Documentary(youtube)",
//                "https://i.ytimg.com/vi/33waoauq01w/maxresdefault.jpg",
//                "https://www.youtube.com/watch?v=33waoauq01w"
//
//            )
//        )
//
//        myRef.push().setValue(
//            contentmodel(
//"10 Crazy Things You Didn't Know About PSY(youtube)",
//                "https://i.ytimg.com/vi/1_HB5og9skE/maxresdefault.jpg",
//                "https://www.youtube.com/watch?v=1_HB5og9skE"
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