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

class KpopActivity : AppCompatActivity() {


    //31.database에 관한 것을 불러올 때 databasereference를 불러온다. 이거는 외워야 한다.
    lateinit var myRef : DatabaseReference


    //어뎁터를 불러오는 것은 똑같다.
    lateinit var rvadapter : contentrvadapter

    //북마크의 id들을 가져오는 함수를 하나 만든다.
    val bookmarkidlist = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kpop)
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
        myRef = database.getReference("k-pop")











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


        val history1 = database.getReference("k-pop")

//        myRef.push().setValue(
//            contentmodel(
//
//                "K-pop/wikipedia"
//            ,"https://upload.wikimedia.org/wikipedia/commons/thumb/6/63/Psy_2012.jpg/255px-Psy_2012.jpg",
//                "https://en.wikipedia.org/wiki/K-pop"
//            )
//        )
//        myRef.push().setValue(
//            contentmodel(
//                "2000's k-pop in youtube!",
//                "https://www.hellokpop.com/wp-content/uploads/2020/07/K-Pop-groups.png",
//                "https://www.youtube.com/results?search_query=2000%27s+kpop"
//
//            )
//        )
//        myRef.push().setValue(
//            contentmodel(
//                "2010's k-pop in youtube!"
//            ,"https://pm1.narvii.com/6480/f81df0b77554ce221856768c0717064bc7073dda_hq.jpg"
//            ,"https://www.youtube.com/results?search_query=2010%27s+kpop"
//
//            )
//        )
//        myRef.push().setValue(
//            contentmodel(
//                "2020's k-pop in youtube!",
//                "https://www.allkpop.com/upload/2020/12/content/311459/1609444780-top25female.jpg",
//                "https://www.youtube.com/results?search_query=2020%27s+kpop"
//            )
//        )
//        myRef.push().setValue(
//            contentmodel("K-pop playlist in youtube!",
//                "data:image/png;base64,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",
//                "https://www.youtube.com/results?search_query=k-pop+playlist"
//
//            )
//        )
//
//        myRef.push().setValue(
//            contentmodel(
//                "KPOP-STORE(케이팝스토어)-Idol Goods Sales Onlinde Site.",
//                "https://pbs.twimg.com/profile_images/1308291833311551489/oHZhDPNq_400x400.jpg",
//                "https://en.kpopstore.co.kr/"
//            )
//        )
//
//        myRef.push().setValue(
//            contentmodel(
//                "K-pop radar/today's k-pop, fandom data observation,each artist's youtube channel and etc.",
//                "https://www.kpop-radar.com/@resources/images/kpopradar_share_thumb-1.jpg",
//                "https://www.kpop-radar.com/"
//
//            )
//        )
//
//        myRef.push().setValue(
//            contentmodel(
//                "KTOWN4U.COM-World's best KPOP, Goods, K-food and K-Beauty.",
//                "https://www.ktown4u.com/theme/b2c2018/new_m/images/logo.png",
//                "https://www.ktown4u.com/"
//            )
//        )
//
//        myRef.push().setValue(
//            contentmodel(
//                "K-pop lover's paradise.",
//                "https://kpopmart.com/themes/leo_goldday/assets/img/modules/appagebuilder/images/png_2020_kpopmartlogo_white_headxhanteo_300x105.png",
//                "https://kpopmart.com/"
//            )
//        )
//        myRef.push().setValue(
//            contentmodel(
//                "Kpop USA-Kpop USA",
//                "https://kpopusaonline.com/wp-content/uploads/2019/11/kpop_logo.png",
//                "https://www.kpopusaonline.com/"
//            )
//        )
//
//        myRef.push().setValue(
//            contentmodel(
//                "World's Best KPOP Collections from Seoul,Korea",
//                "https://cdn.shopify.com/s/files/1/0247/0871/0485/files/kpopalbums_logo3.png?v=1614392115",
//                "https://www.kpopalbums.com/"
//            )
//        )
//
//        myRef.push().setValue(
//            contentmodel(
//                "Soompi-Breaking K-Pop and K-Drama News, Exclusives, and Videos.",
//                "https://0.soompi.io/soompi-web/share-soompi-en.jpg",
//                "https://www.soompi.com/"
//            )
//        )
//        myRef.push().setValue(
//            contentmodel(
//                "Kpop Online Store | Where To Buy Kpop Merchandise Online",
//                "https://i0.wp.com/bemariekorea.com/wp-content/uploads/BE-MARIE-Korea.png?resize=768%2C156&ssl=1"
//            ,"https://bemariekorea.com/kpop-online-store/"
//
//            )
//        )
//        myRef.push().setValue(
//            contentmodel(
//                "KPOP REPUBLIC · PRE-ORDER · HOT ITEM · CD & DVD · LIGHT STICK · MERCHANDISE · BOOK & MAGAZINE · POSTER.",
//                "https://cdn.shopify.com/s/files/1/2248/9053/files/KPR_logo_3581476c-e905-4bfd-a2db-49d584af46dd_540x.png?v=1517055317",
//                "https://www.kpoprepublic.com/"
//
//            )
//        )
//        myRef.push().setValue(
//            contentmodel(
//                "Koreaboo - breaking k-pop news, photos and viral videos",
//                "https://lever-client-logos.s3.amazonaws.com/d18c16f5-bf7d-4066-9d8d-367ca8055b95-1540542618009.png",
//                "https://www.koreaboo.com/"
//
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