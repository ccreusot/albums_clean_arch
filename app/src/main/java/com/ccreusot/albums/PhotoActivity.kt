package com.ccreusot.albums

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_PHOTO_URL = "extra_photo_url"

        @JvmStatic
        fun startActivity(context : Context, url : String) {
            context.startActivity(Intent(context, PhotoActivity::class.java).putExtra(EXTRA_PHOTO_URL, url))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        if (intent.hasExtra(EXTRA_PHOTO_URL)) {
            Picasso.get()
                    .load(intent.getStringExtra(EXTRA_PHOTO_URL))
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(photoImageView)
        }
    }
}
