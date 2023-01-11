package com.allaboutkorea.allaboutkorea.contentslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.allaboutkorea.allaboutkorea.R


//32.URL 정보를 받아서 실제로 웹사이트를 보여주는 엑티비티이다.
class ContentShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contentshowadtivity)




        //contentlistactivity에서 보내준 데이터를 받는 코드이다.

        //url을 받고, 실제로 잘 받아졌는지 확인하는 코드이다.
        val geturl = intent.getStringExtra("url")
        //url을 보여 주고 싶다면, 아래의 코드를 활성화 시켜라.
        //Toast.makeText(this,geturl, Toast.LENGTH_LONG).show()


        //실제로 url을 받아서 화면을 보여주는 코드이다.
        val webView : WebView = findViewById(R.id.webView)
        webView.loadUrl(geturl.toString())

    }
}