package com.AllAboutKorea.allaboutkorea.contentslist

import android.content.Context
import android.content.Intent
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


//이 어뎁터는 모든 분야의 게시판을 불러 올 때 쓰인다.

class contentrvadapter (val context : Context,
                        val items : ArrayList<contentmodel>,
                        val keylist : ArrayList<String>,
                        val bookmarkidlist : MutableList<String>, )
    : RecyclerView.Adapter<contentrvadapter.Viewholder>(){

//   interface ItemClick{
//       fun OnClick (view : View, position:Int)
//   }
//
//    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): contentrvadapter.Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item,parent,false)
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: contentrvadapter.Viewholder, position: Int) {

        //아이템을 뽑을 때, items 뿐만 아니라, keylist도 활용을 해서 키값도 뽑아낸다.

        holder.bindItems(items[position],keylist[position])


//        if(itemClick!=null){
//            holder.itemView.setOnClickListener { v->
//                itemClick?.OnClick(v,position)
//            }
//        }


    }

    //아이템의 개수가 몇개인가
    override fun getItemCount(): Int {
        return items.size
    }


    //아이템을 하나하나 넣어주는 역활
    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bindItems(item : contentmodel,key:String){

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
            if(bookmarkidlist.contains(key)){
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
                if(bookmarkidlist.contains(key)){

                    Toast.makeText(context,"Bookmark has been successfully removed from list.",Toast.LENGTH_SHORT).show()
                    bookmarkarea.setImageResource(R.drawable.bookmark_white)
                    FBRef.bookmarkref
                        .child(FBAuth.getUid())
                        .child(key)
                        .removeValue()
                }


                //북마크가 없을 때의 코드이다.
                //새롭게 데이터를 설정해 주는 코드이다.
                else{Toast.makeText(context,"Bookmark has been successfully added.",Toast.LENGTH_SHORT).show()
                    FBRef.bookmarkref

                .child(FBAuth.getUid())
                .child(key)
                .setValue(bookmarkmodel(true))
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