package com.doggy.mybrary

import android.widget.LinearLayout
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class PostAdapter(
    private val context: Context,
    private var PostList: OrderedRealmCollection<Post>?,
    private var listener: OnItemClickListener,
    private val autoUpdate: Boolean
) :
    RealmRecyclerViewAdapter<Post, PostAdapter.PostViewHolder>(PostList, autoUpdate) {

    override fun getItemCount(): Int = PostList?.size ?: 0

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post: Post = PostList?.get(position) ?: return

        holder.container.setOnClickListener{
            listener.onItemClick(post)
        }
        holder.pageTextView.text = "～p" + post.nowPage.toString()
        holder.persentTextView.text = post.nowPersent.toString() + "%"
        holder.sentence1TextView.text = "・" + post.sentence1
        holder.sentence2TextView.text = "・" + post.sentence2
        holder.sentence3TextView.text = "・" + post.sentence3
        holder.dateTextView.text =
            SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE).format(post.createdAt)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PostViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false)
        return PostViewHolder(v)
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container : LinearLayout = view.container
        val pageTextView: TextView = view.pageTextView
        val persentTextView: TextView = view.persentTextView
        val sentence1TextView: TextView = view.sentence1View
        val sentence2TextView: TextView = view.sentence2View
        val sentence3TextView: TextView = view.sentence3View
        val dateTextView: TextView = view.dateTextView
    }

    interface OnItemClickListener {
        fun onItemClick(item: Post)
    }
}