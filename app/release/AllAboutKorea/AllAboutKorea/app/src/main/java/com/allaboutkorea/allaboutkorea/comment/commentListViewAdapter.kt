package com.allaboutkorea.allaboutkorea.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.allaboutkorea.allaboutkorea.R


//어뎁터의 세세한 형식은 외워야 할 수 밖에 없다.
//별 수 없지 뭐....
class commentListViewAdapter (val commentList : MutableList<commentmodel>) : BaseAdapter() {
    override fun getCount(): Int {

        return commentList.size
    }

    override fun getItem(position: Int): Any {
        return commentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView


        //listview의 중첩출력 현상과 관련해 에러를 줄이기 위한 코드이다.
        if (view == null) {

        view = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item, parent, false)

        }


        val title = view?.findViewById<TextView>(R.id.titleArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)



        title!!.text = commentList[position].commentTitle
        time!!.text = commentList[position].commentCreatedTime

        return view!!
    }

}