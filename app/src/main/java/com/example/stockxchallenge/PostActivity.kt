package com.example.stockxchallenge

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class PostActivity : AppCompatActivity() {

    private val TAG = "PostActivity"
    private val postPermaLinkKey = "postURL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        if (extras != null) {
            val webView = WebView(this)
            val permalink = extras.get(postPermaLinkKey) as String
            Log.d(TAG, permalink)
            webView.loadUrl(getString(R.string.reddit_base_url) + permalink)
            setContentView(webView)
        }
    }
}