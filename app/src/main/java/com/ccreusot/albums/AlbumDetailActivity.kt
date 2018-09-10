package com.ccreusot.albums

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.ccreusot.albums.adapters.AlbumDetailAdapter
import com.ccreusot.albums.interactors.AlbumDetailInteractor
import com.ccreusot.albums.presentations.AlbumDetailPresenterImpl
import com.ccreusot.albums.presentations.AlbumDetailView
import com.ccreusot.albums.presentations.AlbumDetailViewDecorator
import com.ccreusot.albums.repositories.AlbumDetailRepositoryImpl
import com.ccreusot.albums.repositories.CacheAlbumsRepository
import com.ccreusot.albums.repositories.RetrofitAlbumsRepository
import com.ccreusot.albums.viewmodels.PhotoViewModel
import kotlinx.android.synthetic.main.activity_layout_list.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch

class AlbumDetailActivity : AppCompatActivity(), AlbumDetailView {

    companion object {
        private const val DISPLAY_LOADER = 0
        private const val DISPLAY_ERROR = 1
        private const val DISPLAY_LIST = 2

        private const val EXTRA_ALBUM_ID = "extra_album_id"

        // note needed but in case it's used with java code we can set it.
        @JvmStatic
        fun startActivity(context : Context, albumId : Long) {
            context.startActivity(Intent(context, AlbumDetailActivity::class.java).putExtra(EXTRA_ALBUM_ID, albumId))
        }
    }

    private val decoractor = AlbumDetailViewDecorator(this)
    private val interactor = AlbumDetailInteractor(
            AlbumDetailPresenterImpl(decoractor),
            AlbumDetailRepositoryImpl(CacheAlbumsRepository(this, RetrofitAlbumsRepository()))
    )
    private var retrieveAlbumJob : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_list)

        contentViewFlipper.displayedChild = DISPLAY_LOADER

        itemRecyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        itemRecyclerView.adapter = AlbumDetailAdapter()

        if (intent.hasExtra(EXTRA_ALBUM_ID)) {
            retrieveAlbumJob = launch {
                interactor.fetchAlbum(intent.getLongExtra(EXTRA_ALBUM_ID, -1))
            }
        }
    }

    override fun onDestroy() {
        decoractor.destroy()
        retrieveAlbumJob?.cancel()
        retrieveAlbumJob = null
        super.onDestroy()
    }

    override fun displayAlbumIdNegative() {
        contentViewFlipper.displayedChild = DISPLAY_ERROR
        errorTextView.text = getString(R.string.activity_album_detail_error_negative_id)
    }

    override fun displayAlbumError() {
        contentViewFlipper.displayedChild = DISPLAY_ERROR
        errorTextView.text = getString(R.string.generic_error_from_server)
    }

    override fun displayPhotos(photoViewModelList: List<PhotoViewModel>) {
        contentViewFlipper.displayedChild = DISPLAY_LIST
        (itemRecyclerView.adapter as AlbumDetailAdapter).listPhotoViewModel = photoViewModelList
        (itemRecyclerView.adapter as AlbumDetailAdapter).onClickPhoto = this::onClick
    }

    fun onClick(url: String) {
        PhotoActivity.startActivity(this, url)
    }
}
