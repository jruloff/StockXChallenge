package com.example.stockxchallenge

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.posts_recycler_view.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var rvAdapter : PostsRecyclerViewAdapter

    private var jsonExtension : String = ".json"

    private var postsArr : ArrayList<SubRedditPostData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        linearLayoutManager = LinearLayoutManager(this)
        postsRecyclerView.layoutManager = linearLayoutManager
        rvAdapter = PostsRecyclerViewAdapter(postsArr)
        postsRecyclerView.adapter = rvAdapter

        val rvDecoration = DividerItemDecoration(postsRecyclerView.context, linearLayoutManager.orientation)
        postsRecyclerView.addItemDecoration(rvDecoration)

        //configure
        setupSearchView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.search_bar, menu)
        return true
    }

    //configure query text listeners
    private fun setupSearchView() {
        redditSearch.setQueryHint(getString(R.string.subreddit_search_hint))

        redditSearch.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s : String) : Boolean {
                //configure and perform http request
                retrieveSubRedditPosts(s)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    //perform GET to retrieve subreddit posts
    private fun retrieveSubRedditPosts(subreddit : String) : JSONArray {
        var subredditURLString = getString(R.string.subreddit_base_url) + subreddit + jsonExtension

        val subRedditPostsJSONObjectRequest = JsonObjectRequest(
            Request.Method.GET, subredditURLString, null,
            Response.Listener { response ->
                var updatedSearchPostsList = SubRedditJSONParser(response).postsList

                //clear existing posts list and repopulate based on search results
                postsArr.clear()

                for(post in updatedSearchPostsList) {
                    postsArr.add(post)
                }

                rvAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Log.d(TAG, error.toString())
            }
        )

        APIRequestManager.getInstance(this).addToRequestQueue(subRedditPostsJSONObjectRequest)

        return JSONArray()
    }
}
