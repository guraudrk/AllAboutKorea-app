package com.AllAboutKorea.allaboutkorea.contentslist

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.AllAboutKorea.allaboutkorea.R
import com.AllAboutKorea.allaboutkorea.utils.FBAuth
import com.AllAboutKorea.allaboutkorea.utils.FBRef

//30.북마크 리스트를 불러오는 데에 필요한 어뎁터이다.
//이놈도 아직 작동 원리를 잘 모르기 때문에 복습이 필요하다.
//이놈은 컨텐츠 어뎁터와 구조가 거의 비슷하기 때문에,
//컨텐츠 어뎁터의 코드를 복붙한다. 단, 이름은 바꾼다.
//여기서도 어뎁터를 선언하는 이유는, 결국
//북마크의 '리스트'를 불러 오는 것인데, 이를 보여주는 형태가
//바로 리사이클러뷰이기 때문이다.
class BookmarkRVAdapter(val context : Context,
                        val items : ArrayList<contentmodel>,
                        val keyList : ArrayList<String>,
                        val bookmarkIdList : MutableList<String>)

    : RecyclerView.Adapter<BookmarkRVAdapter.Viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkRVAdapter.Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item, parent, false)

        Log.d("BookmarkRVAdapter", keyList.toString())
        Log.d("BookmarkRVAdapter", bookmarkIdList.toString())
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: BookmarkRVAdapter.Viewholder, position: Int) {
        holder.bindItems(items[position], keyList[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item : contentmodel, key : String) {

            //아이템을 클릭하면 이런 코드가 실행이 된다.
            //아이템을 클릭하면 웹사이트로 이동하게끔 하는 코드이다.
            itemView.setOnClickListener {
                //밑에 'title1'이런 식으로 표시가 된다.
                //Toast.makeText(context,item.title, Toast.LENGTH_SHORT).show()
                val intent = Intent(context,ContentShowActivity::class.java)
                //intent.putextra는 해당 항목을 넘겨받는 역활을 한다.
                //아래 코드는 item.weburl의 url값을 넘겨받는다는 의미이다.
                intent.putExtra("url",item.webUrl)
                itemView.context.startActivity(intent)

            }

            val contentTitle = itemView.findViewById<TextView>(R.id.textarea_bookmark)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imagearea_bookmark)
            val bookmarkarea = itemView.findViewById<ImageView>(R.id.bookmarkarea_bookmark)




            //만약 bookmarkidlist가 key에 대한 정보를 가지고 있는지에 따라 색칠을 해준다.
            if(bookmarkIdList.contains(key)){
                bookmarkarea.setImageResource(R.drawable.bookmark_color)
            }
            else{
                bookmarkarea.setImageResource(R.drawable.bookmark_white)
            }


            //북마크 클릭 이벤트를 발생시키자.
            //북마크를 클릭하면 넣어줄지 안넣어줄지를 결정하는 코드이다.

            bookmarkarea.setOnClickListener {
                //Toast.makeText(context,key, Toast.LENGTH_SHORT).show()

                //uid안에 데이터를 집어 넣는 것이다.
                //클릭을 하면,북마크 저장에 관한 데이터를 받아오는 것이다.

                //북마크가 있을 때의 코드이다.
                //북마크가 이미 있다면,리스트에서 삭제를 해 줘야 할 것이다.
                if(bookmarkIdList.contains(key)){

                    Toast.makeText(context,"Bookmark has been successfully removed from list.",Toast.LENGTH_SHORT).show()

                    FBRef.bookmarkref
                        .child(FBAuth.getUid())
                        .child(key)
                        .removeValue()
                    bookmarkarea.setImageResource(R.drawable.bookmark_white)
                }


                //북마크에 없을 때의 코드이다.
                //새롭게 데이터를 설정해 주는 코드이다.
                //근데 여긴 bookmark어뎁터니까 딱히 필요가 없을 거다.
                else{Toast.makeText(context,"Bookmark has been successfully added.",Toast.LENGTH_SHORT).show()
                    FBRef.bookmarkref

                        .child(FBAuth.getUid())
                        .child(key)
                        .setValue(bookmarkmodel(true))
                    bookmarkarea.setImageResource(R.drawable.bookmark_color)
                }
            }
            contentTitle.text = item.title

            //glide를 통해 이미지를 불러오는 코드이다.
            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)
        }
    }


}