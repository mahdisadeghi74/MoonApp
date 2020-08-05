package com.moon.ui.story.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moon.R
import com.moon.model.JoinStory
import kotlinx.android.synthetic.main.layout_join_request_item.view.*

class JoinRequestAdapter : RecyclerView.Adapter<JoinRequestAdapter.ViewHolder>() {
    private var joinStories = ArrayList<JoinStory>()
    var changeStatusListener: ((JoinStory, Int) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(joinStory: JoinStory, changeStatusListener: ((JoinStory, Int) -> Unit)?){
            itemView.tvUserName.text = joinStory.userName
            itemView.chbJoinRequest.isChecked = joinStory.isAccepted

            itemView.chbJoinRequest.setOnCheckedChangeListener { buttonView, isChecked ->
                joinStory.isAccepted = isChecked
                changeStatusListener?.let { function ->
                    function(joinStory, adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JoinRequestAdapter.ViewHolder {
      return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_join_request_item, parent, false))
    }

    override fun getItemCount(): Int {
       return joinStories.size
    }

    override fun onBindViewHolder(holder: JoinRequestAdapter.ViewHolder, position: Int) {
        holder.bind(joinStory = joinStories[position], changeStatusListener = changeStatusListener)
    }

    fun addJoinRequest(joinRequests: ArrayList<JoinStory>?){
        if (joinRequests != null) {
            this.joinStories.clear()
            this.joinStories.addAll(joinRequests)
        }
        notifyDataSetChanged()
    }

    fun updateJoinRequest(joinStory: JoinStory?){
        if (joinStory != null) {
            this.joinStories.forEachIndexed { index, js ->
                if (joinStory.id == js.id) {
                    js.isAccepted = joinStory.isAccepted
                    js.isActive = joinStory.isActive
                    return
                }
            }
        }
    }
}