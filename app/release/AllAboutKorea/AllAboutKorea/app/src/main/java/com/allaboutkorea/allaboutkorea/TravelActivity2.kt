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

class TravelActivity2 : AppCompatActivity() {



        //31.database에 관한 것을 불러올 때 databasereference를 불러온다. 이거는 외워야 한다.
        lateinit var myRef : DatabaseReference


        //어뎁터를 불러오는 것은 똑같다.
        lateinit var rvadapter : contentrvadapter

        //북마크의 id들을 가져오는 함수를 하나 만든다.
        val bookmarkidlist = mutableListOf<String>()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_travel)
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
            myRef = database.getReference("travel")











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

//            myRef.push().setValue(
//                contentmodel(
//                    "VisitKorea - Imagine Your Korea",
//                    "https://english.visitkorea.or.kr/enu/img/comm/logo.png",
//                    "https://english.visitkorea.or.kr/enu/index.kto"
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "South Korea 2022: Best Places to Visit - Tripadvisor",
//                    "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1b/33/f2/f0/caption.jpg?w=700&h=-1&s=1",
//                    "https://www.tripadvisor.com/Tourism-g294197-Seoul-Vacations.html#/media/294197/"
//
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "KoreaTravelEasy: Your KOREA TRAVEL made EASY",
//                    "https://cdn.koreatraveleasy.com/wp-content/uploads/2016/07/22175325/kte_logo_600x400.png",
//                    "https://www.koreatraveleasy.com/"
//
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "The Official Travel Guide to Seoul - Visit Seoul",
//                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZuc6MqGSH0KTJoW6u_PHsIBgFiU1aY340A-JRuBYOeUxI3tDoHn-f&usqp=CAE&s",
//                    "https://english.visitseoul.net/index"
//
//                )
//            )
//
//            myRef.push().setValue(
//                contentmodel(
//"50 South Korea Travel Tips - There She Goes Again",
//                    "https://thereshegoesagain.org/wp-content/uploads/2017/09/south-korea-travel-tips.jpg",
//                    "https://thereshegoesagain.org/south-korea-travel-tips/"
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "The 10 Best Hotels (2022) - Korea Vacation",
//                    "https://media-cdn.tripadvisor.com/media/photo-s/23/a2/9d/1e/2022-reopening-picture.jpg",
//                    "https://www.tripadvisor.com/Hotels-g294197-Seoul-Hotels.html"
//
//
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "Korea Tours - Top Things to Do With Prices",
//                    "https://d1blyo8czty997.cloudfront.net/seller-profiles/160231/350x350/9203395143.jpg",
//                    "https://www.viator.com/South-Korea-tourism/d972-r20149641011-s249438971?mcid=28353&tsem=true&supci=-1790757593&supag=20149641011&supsc=kwd-367319007797&supai=275956668628&supap=&supdv=c&supnt=g&supti=kwd-367319007797&suplp=1009871&supli=&m=28353&supag=20149641011&supsc=kwd-367319007797&supai=275956668628&supap=&supdv=c&supnt=nt%3Ag&suplp=1009871&supli=&supti=kwd-367319007797&tsem=true&supci=kwd-367319007797&supap1=&supap2=&gclid=Cj0KCQjwxb2XBhDBARIsAOjDZ35y2ympqytBz0tv6qeHYufKInmVHBD7__tAQeZCg9NFLvwMPGT3HYMaAsxnEALw_wcB"
//
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "Everything you need to know before traveling to South Korea",
//                    "data:image/png;base64,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",
//                    "https://www.lonelyplanet.com/articles/things-to-know-before-traveling-to-south-korea"
//
//
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "South Korea travel - Lonely Planet | Asia",
//                    "data:image/png;base64,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",
//                    "https://www.lonelyplanet.com/south-korea"
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "Book Best Things to Do in Korea - Korea's #1 Travel Shop",
//                    "data:image/png;base64,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",
//                    "https://www.trazy.com/ko"
//
//                )
//            )
//
//            myRef.push().setValue(
//                contentmodel(
//                    "Korea Electronic Travel Authorization website > K-ETA",
//                    "https://www.1koreanpost.com/uploads/news/posts/k-eta.jpg",
//                    "https://www.k-eta.go.kr/portal/apply/index.do?locale=EN"
//                )
//            )
//
//            myRef.push().setValue(
//                contentmodel(
//                    "Be Marie Korea | A South Korea Travel Blog",
//                    "https://i0.wp.com/bemariekorea.com/wp-content/uploads/BE-MARIE-Korea.png?resize=768%2C156&ssl=1",
//                    "https://bemariekorea.com/"
//                )
//            )
//
//            myRef.push().setValue(
//                contentmodel(
//                    "South Korea Travel Guide-CNN",
//                "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_750,c_fill,g_auto,h_422,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F170706113328-south-korea.jpg"
//                ,"https://edition.cnn.com/travel/destinations/south-korea"
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "Incheon International Airport-Best Airport In the world",
//                    "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e8/Seoul_Incheon_Airport_%2827833094934%29.jpg/375px-Seoul_Incheon_Airport_%2827833094934%29.jpg",
//                    "https://en.wikipedia.org/wiki/Incheon_International_Airport"
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "7 gorgeous mountain hikes in South Korea-cnn.com",
//                    "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_750,c_fill,g_auto,h_422,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F170418160821-hallasan.jpg",
//                    "https://edition.cnn.com/travel/article/koreas-best-hikes/index.html"
//
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//
//                    "Korea Mountains - PeakVisor",
//                    "https://peakvisor.com/photo/South-Korea-Bukhansan-National-Park.jpg",
//                    "https://peakvisor.com/adm/korea.html"
//                )
//            )
//
//            myRef.push().setValue(
//                contentmodel(
//                    "10 Beautiful Mountain Trails in Korea Perfect for Hikes",
//                    "https://a.cdn-hotels.com/gdcs/production46/d555/4e9356f6-b88a-4946-9135-0361a9a09972.jpg?impolicy=fcrop&w=1600&h=1066&q=medium",
//                    "https://www.hotels.com/go/south-korea/best-hiking-trails-korea?pos=HCOM_ASIA&locale=en_KR"
//                )
//
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "25 Seoul Restaurants You'll Want to Fly For",
//                    "https://www.willflyforfood.net/wp-content/uploads/2020/01/seoul-food-guide-pinterest.jpg.webp",
//                    "https://www.willflyforfood.net/seoul-food-guide-25-must-eat-restaurants-in-seoul-south-korea/"
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "11 Best Pubs and Bars in Seoul",
//                    "https://a.cdn-hotels.com/gdcs/production21/d1001/b8ad9330-c6ec-4a76-8031-5f0701a5849a.jpg?impolicy=fcrop&w=1600&h=1066&q=medium",
//                    "https://www.hotels.com/go/south-korea/best-seoul-pubs-bars?pos=HCOM_ASIA&locale=en_KR"
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "6 Must-See Seoul Palaces For Your South Korea Adventure",
//                    "https://www.treksplorer.com/wp-content/uploads/seoul-palaces.jpg",
//                    "https://www.treksplorer.com/seoul-palaces/"
//                )
//            )
//            myRef.push().setValue(
//                contentmodel(
//                    "The 15 Best Places for Palaces in Seoul - Foursquare",
//                    "https://fastly.4sqi.net/img/general/1398x536/11749965_bqBCXIu_eQzOoWKpUUw5MUySstaamjIzpXtJxO36W1U.jpg",
//                    "https://foursquare.com/top-places/seoul/best-places-palaces"
//                )
//            )

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