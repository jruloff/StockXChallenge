package com.example.stockxchallenge

import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Array

//class used to parse json response
class SubRedditJSONParser(posts : JSONObject) {
    var postsList = mutableListOf<SubRedditPostData>()
    init {
        var postsJSONObject = posts["data"] as JSONObject
        var postObjects = postsJSONObject["children"] as JSONArray

        for(i in 0..(postObjects.length()-1)) {
            val postJSONData = postObjects.getJSONObject(i)["data"] as JSONObject

            val postTitle = postJSONData["title"] as String
            val postSubreddit = postJSONData["subreddit_name_prefixed"] as String
            val postData = SubRedditPostData(postTitle, postSubreddit)
            postsList.add(postData)
            print(postsList.size)
        }
    }
}