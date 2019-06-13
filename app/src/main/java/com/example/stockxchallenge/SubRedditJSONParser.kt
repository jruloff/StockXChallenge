package com.example.stockxchallenge

import org.json.JSONArray
import org.json.JSONObject

//class used to parse json response
class SubRedditJSONParser(posts : JSONObject) {

    private val dataKey = "data"
    private val childrenKey = "children"
    private val titleKey = "title"
    private val subredditNamePrefixedKey = "subreddit_name_prefixed"
    private val permalinkKey = "permalink"
    private val thumbnailKey = "thumbnail"

    var postsList : ArrayList<SubRedditPostData> = ArrayList()

    init {
        parseJSONToSubredditPostData(posts)
    }

    private fun parseJSONToSubredditPostData(posts : JSONObject) {
        var postsJSONObject = posts[dataKey] as JSONObject
        var postObjects = postsJSONObject[childrenKey] as JSONArray

        //parse json object and convert to SubRedditPostData
        for(i in 0..(postObjects.length()-1)) {
            val postJSONData = postObjects.getJSONObject(i)[dataKey] as JSONObject
            val postTitle = postJSONData[titleKey] as String
            val postSubreddit = postJSONData[subredditNamePrefixedKey] as String
            val postURL = postJSONData[permalinkKey] as String
            val thumbnailURL = postJSONData[thumbnailKey] as String
            val postData = SubRedditPostData(postTitle, postSubreddit, postURL, thumbnailURL)

            //verify obj contains a title and is not parent subreddit description
            if (!postData.title.isEmpty()) {
                postsList.add(postData)
            }
        }
    }
}