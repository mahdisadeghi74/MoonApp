package com.moon.ui.story.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moon.R
import com.moon.model.Story
import kotlinx.android.synthetic.main.layout_story_item.view.*

class StoriesAdapter : RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {
    private var stories: ArrayList<Story>? = null
    var onClickListenerItem: ((Story, Int, View) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.transitionName = "storyView"
        }
        fun bindView(story: Story, onClickListenerItem: ((Story, Int, View) -> Unit)? = null) {
            itemView.tvStoryTitle.text = story.title
            itemView.tvStoryContent.text = story.content
            itemView.tvStoryWriter.text = story.created_by
            itemView.tvClapsCount.text = story.clapCount.toString()
            itemView.setOnClickListener {
                onClickListenerItem?.let { function ->
                    function(story, adapterPosition, it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_story_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return stories?.size ?: 0
    }

    override fun onBindViewHolder(holder: StoriesAdapter.ViewHolder, position: Int) {
        stories?.get(position)?.let { holder.bindView(it, onClickListenerItem) }
    }

    fun setStories(stories: ArrayList<Story>?) {
        if (this.stories == null)
            this.stories = stories
        else {
            this.stories?.clear()
            stories?.let { this.stories?.addAll(it) }
        }
        notifyDataSetChanged()
    }
}