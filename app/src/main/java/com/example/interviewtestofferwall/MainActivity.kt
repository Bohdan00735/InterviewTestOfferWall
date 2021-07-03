package com.example.interviewtestofferwall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private var listOfId: ArrayList<String>? = null
    private var counter = -1
    lateinit var imageView:ImageView
    lateinit var webView: WebView
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOfId = NetworkLoader().getIdList()!!

        imageView = findViewById(R.id.imageView)
        webView = findViewById(R.id.webView)
        textView = findViewById(R.id.textView)
        showNext()

        findViewById<Button>(R.id.button).setOnClickListener{
            showNext()
        }
    }

    private fun showNext() {
        if(listOfId == null) return
        counter++
        if (counter >= listOfId!!.size) counter = 0
        val id = listOfId!![counter]
        when(NetworkLoader().getResourceById(id,"type")){
            "text"-> showText(id)
            "webview"-> showWebView(id)
            "image"-> showImage(id)
            "game" -> showNext()
        }
    }
    private fun hideAll(){
        imageView.visibility = View.INVISIBLE
        textView.visibility = View.INVISIBLE
        webView.visibility = View.INVISIBLE
    }

    private fun showImage(id: String) {
        hideAll()
        Picasso.with(applicationContext)
            .load(NetworkLoader().getResourceById(id,"url"))
            .placeholder(R.drawable.ic_baseline_landscape_24)
            .into(imageView)
        imageView.visibility = View.VISIBLE
    }

    private fun showWebView(id: String) {
        hideAll()
        NetworkLoader().getResourceById(id,"url")?.let { webView.loadUrl(it) }
        webView.visibility = View.VISIBLE
    }

    private fun showText(id: String) {
        hideAll()
        textView.text = NetworkLoader().getResourceById(id,"message")?: return
        textView.visibility = View.VISIBLE
    }


}