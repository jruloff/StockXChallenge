package com.example.stockxchallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.posts_recycler_view.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    private var searchQueryHint : String = "search for a subreddit..."
    private var baseRedditURL : String = "https://www.reddit.com/r/"
    private var jsonExtension : String = ".json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayoutManager = LinearLayoutManager(this)
        postsRecyclerView.layoutManager = linearLayoutManager

        //configure search bar and search view
        setupSearchView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        menuInflater.inflate(R.menu.search_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //configure query text listeners
    private fun setupSearchView() {
        redditSearch.setQueryHint(searchQueryHint)

        redditSearch.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s : String) : Boolean {
                retrieveSubRedditPosts(s)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                print("NewString" + newText)
                return false
            }
        })
    }

    //perform GET to retrieve subreddit posts
    private fun retrieveSubRedditPosts(subreddit : String) : JSONArray {
        var subredditURLString = baseRedditURL + subreddit + jsonExtension

        val subRedditPostsJSONObjectRequest = JsonObjectRequest(
            Request.Method.GET, subredditURLString, null,
            Response.Listener { response ->
                print(response.toString())
                var parsedJSON = SubRedditJSONParser(response).postsList
                print(parsedJSON)
            },
            Response.ErrorListener { error ->
                print(error.toString())
            }
        )

        APIRequestManager.getInstance(this).addToRequestQueue(subRedditPostsJSONObjectRequest)

        return JSONArray()
    }
}
