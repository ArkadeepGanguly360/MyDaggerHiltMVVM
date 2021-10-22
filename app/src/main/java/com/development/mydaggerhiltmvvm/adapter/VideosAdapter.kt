package com.development.mydaggerhiltmvvm.adapter

import android.content.Context
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.VideosAdapterItemBinding
import com.development.mydaggerhiltmvvm.model.VideoItem
import java.util.*

class VideosAdapter(
    val context: Context,
    val list: ArrayList<VideoItem>
) :
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return list[position].hashCode().toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val rowBinding: VideosAdapterItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.videos_adapter_item,
            parent,
            false
        )
        return ViewHolder(rowBinding)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        holder.rowBinding.title = item.videoTitle
        holder.rowBinding.desc = item.videoDesc
        holder.rowBinding.videoView.setVideoURI(Uri.parse(item.videoURL))

        holder.rowBinding.videoView.setOnPreparedListener(OnPreparedListener { mp ->
            holder.rowBinding.progressBar.visibility = View.GONE
            mp.start()
        })
        holder.rowBinding.videoView.setOnCompletionListener(OnCompletionListener { mp -> mp.start() })
    }

    inner class ViewHolder(val rowBinding: VideosAdapterItemBinding) :
        RecyclerView.ViewHolder(rowBinding.root)
}
