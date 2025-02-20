package com.example.myapplication.videoPlayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class VideoAdapter(
    private val listaVideos: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewVideo: TextView = itemView.findViewById(R.id.textViewVideo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = listaVideos[position]
        holder.textViewVideo.text = video
        holder.itemView.setOnClickListener { onItemClick(video) }
    }

    override fun getItemCount(): Int = listaVideos.size
}
