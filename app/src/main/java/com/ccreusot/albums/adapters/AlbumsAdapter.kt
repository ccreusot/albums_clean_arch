package com.ccreusot.albums.adapters

import android.support.v4.content.ContextCompat
import android.support.v4.view.LayoutInflaterCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ccreusot.albums.R
import com.ccreusot.albums.viewmodels.AlbumViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_layout_album.view.*
import java.util.zip.Inflater

class AlbumsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onClick : ((positon : Int) -> Unit)? = null

    var albumViewModelList: List<AlbumViewModel>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_layout_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun getItemCount(): Int {
        return albumViewModelList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val albumViewModel = albumViewModelList?.get(position)
        if (albumViewModel != null) {
            val thumbnails = albumViewModel.getThumbnails()
            val listImageView = listOf(holder.itemView.viewHolderThumbnail1,
                    holder.itemView.viewHolderThumbnail2,
                    holder.itemView.viewHolderThumbnail3)
            for (i in 0..(thumbnails.size - 1)) {
                Picasso.get()
                        .load(thumbnails[i])
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(listImageView[i])
            }
            holder.itemView.viewHolderAlbumPhotoCountTextView.text = "+ ${albumViewModel.getPhotoCount() - thumbnails.size}"
            holder.itemView.setOnClickListener {
                this@AlbumsAdapter.onClick?.invoke(position)
            }
        }
    }

    inner class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view)
}