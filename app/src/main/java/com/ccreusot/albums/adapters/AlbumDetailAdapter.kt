package com.ccreusot.albums.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ccreusot.albums.R
import com.ccreusot.albums.viewmodels.PhotoViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_layout_album_photo.view.*

class AlbumDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listPhotoViewModel : List<PhotoViewModel>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_layout_album_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPhotoViewModel?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photoViewModel = listPhotoViewModel?.get(position)
        if (photoViewModel != null) {
            holder.itemView.titleTextView.text = photoViewModel.getTitle()
            Picasso.get()
                    .load(photoViewModel.getThumbnail())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.itemView.thumbnailPhotoImageView)
        }
    }

    inner class PhotoViewHolder(view : View) : RecyclerView.ViewHolder(view)
}