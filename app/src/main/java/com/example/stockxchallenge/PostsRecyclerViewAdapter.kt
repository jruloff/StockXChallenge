package com.example.stockxchallenge

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.posts_recycler_view_item.view.*

class PostsRecyclerViewAdapter(private val posts : ArrayList<SubRedditPostData>) : RecyclerView.Adapter<PostsRecyclerViewAdapter.PostHolder>() {

    protected val TAG = "PostRecyclerViewAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.posts_recycler_view_item,parent, false)
        return PostHolder(inflatedView)
    }

    override fun getItemCount(): Int {

        return posts.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = posts[position]
        holder.bindPost(post)
    }

    class PostHolder(v : View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        protected val TAG = "PostHolder"
        private var view: View = v
        private var post : SubRedditPostData? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d(TAG, "clicked on view")
//            val context = itemView.context
//            val showPhotoIntent = Intent(context, PostActivity::class.java)
//            showPhotoIntent.putExtra(POST_KEY, post)
//            context.startActivity(showPhotoIntent)
        }

        companion object {
            private val POST_KEY = "POST"
        }

        fun bindPost(post: SubRedditPostData) {
            this.post = post
            view.postTitle.text = post.title
            view.postSubreddit.text = post.subreddit
//            Picasso.with(view.context).load(photo.url).into(view.itemImage)
//            view.itemDate.text = photo.humanDate
//            view.itemDescription.text = photo.explanation
        }

    }

}